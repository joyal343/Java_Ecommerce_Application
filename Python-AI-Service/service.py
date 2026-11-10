from typing import TypedDict, List, Union
from langchain_ollama import ChatOllama
from langchain_core.messages import HumanMessage, AIMessage
from langgraph.graph import StateGraph, START, END
import requests

url = "http://127.0.0.1:8080/catalog/product/filter"

payload = {}
headers = {}

# Filter Products Tool
def filterProducts():
    response = requests.request("GET", url, headers=headers, data=payload)
    print(response.text)

class AgentState(TypedDict):
    messages: List[Union[HumanMessage, AIMessage]] # It allows to store Human and AI messages - Datatypes Custom

llm = ChatOllama(model="llama3.2:3b")

def process(state: AgentState) -> AgentState:
    """This node will solve the request you input"""
    output = llm.invoke(state["messages"])
    state["messages"].append(AIMessage(content=output.content))
    print(f"\nAI: {output.content}")
    print("CURRENT STATE:", state["messages"])
    return state

graph = StateGraph(AgentState)

graph.add_node("process",process)

graph.add_edge(START, "process")
graph.add_edge("process",END)

agent = graph.compile()

conversation_history = []

user_input = input("Input :")
while user_input != "exit":
    conversation_history.append(HumanMessage(content=user_input))
    result = agent.invoke({"messages":conversation_history})
    conversation_history = result["messages"]
    user_input = input("Input :")