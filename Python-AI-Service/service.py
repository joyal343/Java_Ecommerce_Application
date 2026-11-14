from typing import TypedDict, List, Union, Annotated, Sequence
from langchain_ollama import ChatOllama
from langchain_core.messages import  BaseMessage
from langchain_core.messages import ToolMessage # Result from tool call
from langchain_core.messages import SystemMessage # System Instructions
from langchain_core.messages import HumanMessage, AIMessage
from langgraph.graph import StateGraph, START, END
from langchain_core.tools import tool
from langgraph.prebuilt import ToolNode
from langgraph.graph.message import add_messages
import requests
from typing import Optional
import time

class AgentState(TypedDict):
    messages: Annotated[Sequence[BaseMessage], add_messages]

@tool
def get_filtered_products(
    category: Optional[str] = None,
    brand: Optional[str] = None,
    maxprice: Optional[int] = None,
    minprice: Optional[int] = None,
):
    """
    This tool is used to fetch different products within the database
    All the parameters provided to this tool is optional

    Parameters
    1. category - category of the product, it is a string or None
    2. brand - brand of the product, it is a string or None
    3. maxprice - maximum price of the product, it is an integer or None
    4. minprice - minimum price of the product, it is an integer or None

    Output
    JSON of list of products according to the category and brand
    """
    query  = "?"
    count = 0
    if category:
        if count > 0:
            query+=","
        query += f"category={category}"
        count +=1
    if brand:
        if count > 0:
            query+=","
        query += f"brand={brand}"
        count += 1
    if minprice:
        if count > 0:
            query+=","
        query += f"brand={minprice}"
        count += 1
    if maxprice:
        if count > 0:
            query+=","
        query += f"brand={maxprice}"
        count += 1


    url = "http://127.0.0.1:8080/catalog/product/filter" + query

    payload = {}
    headers = {}

    response = requests.request("GET", url, headers=headers, data=payload)
    return response.text

@tool
def get_categories():
    """Fetches all categories stored in the database"""

    url = "http://127.0.0.1:8080/catalog/product/category"

    payload = {}
    headers = {}

    response = requests.request("GET", url, headers=headers, data=payload)

    return response.text

@tool
def get_brands():
    """Fetches all brand details stored in the database"""

    url = "http://127.0.0.1:8080/catalog/product/brand"

    payload = {}
    headers = {}

    response = requests.request("GET", url, headers=headers, data=payload)

    return response.text

tools = [get_filtered_products, get_categories, get_brands]

llm = ChatOllama(model="gemma4:e4b").bind_tools(tools)

def model_call(state: AgentState) -> AgentState:
    system_prompt = SystemMessage(content=
                                  """
                                  You are my AI ecommerce assistant, answer the customers query to the best of your ability.
                                  Use tools via the function-calling interface only, never write JSON in your reply.
                                  Before filtering by brand/category - use the get brand/get category tools to check if those ctegories exist first.
                                  If they exist then you can use the get products filtered tool to filter based on brand and category.
                                  Keep In Mind - When searching for a category/brand if it does not exist and you dont know what brands/categories are there check it
                                  then only if you are sure it is not your mistake tell the user it is not there. The user can make mistakes"""
                                  )
    start = time.perf_counter()
    op = llm.invoke([system_prompt]+ state["messages"])
    elapsed = time.perf_counter() - start
    print(f"Tool call took {elapsed:.3f} seconds")
    return {"messages":[op]}

def should_continue(state: AgentState) :
    messages = state["messages"]
    last_message = messages[-1]
    if not last_message.tool_calls:
        return "end"
    else:
        return "continue"

graph = StateGraph(AgentState)

graph.add_node("agent",model_call)

tool_node = ToolNode(tools=tools)
graph.add_node("tools",tool_node)

graph.set_entry_point("agent")
graph.add_conditional_edges("agent",should_continue,{"continue":"tools","end":END})
graph.add_edge("tools","agent")

app = graph.compile()

def print_stream(stream):
    for s in stream:
        message = s["messages"][-1]
        if isinstance(message, tuple):
            print(message)
        else :
            message.pretty_print()

inputs = {"messages":[("user","What are the available products from NIKE" )]}
print_stream(app.stream(inputs,stream_mode="values"))