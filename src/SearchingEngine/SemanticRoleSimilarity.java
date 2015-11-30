package SearchingEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import database.SemanticRoleMatchData;

import module.graph.MakeGraph;
import module.graph.SentenceToGraph;
import module.graph.helper.GraphPassingNode;
import module.graph.helper.Node;
import module.graph.helper.NodePassedToViewer;

public class SemanticRoleSimilarity {
	
	public static ArrayList<SemanticRoleMatchData> semanticRoles = new ArrayList<SemanticRoleMatchData>();
	public static HashMap<String, String> hm = new HashMap<String,String>();
	public static int roleCount = 0;
	public static ArrayList<SemanticRoleMatchData> semanticRoleSimilarity(String[] linesInPlot){
		for(int i=0;i<linesInPlot.length;i++){
			try{
				SentenceToGraph stg = new SentenceToGraph();
				GraphPassingNode gpn2 = stg.extractGraph(linesInPlot[i],false,true,false);
				ArrayList<String> list = gpn2.getAspGraph();
				hm=gpn2.getposMap();
				MakeGraph mg = new MakeGraph();
				
				ArrayList<NodePassedToViewer> graphs = mg.createGraphUsingSentence(linesInPlot[i], false, true, false);
				Iterator<NodePassedToViewer> it = graphs.iterator();
				
				while(it.hasNext()){
					Node nd = it.next().getGraphNode();
					semanticRoleExtraction(nd);
					
				}
			}
			catch(StackOverflowError | NullPointerException e)
			{
				System.err.println("ERROR: "+ linesInPlot[i] );
			}
		}
		return semanticRoles;
	}

	private static void semanticRoleExtraction(Node nd) {
		// splitting current node string to remove '-' part
		String currentNodeValue;
		String[] currentNodeValueTokens = nd.getValue().split("-");
		currentNodeValue = currentNodeValueTokens[0];
		
		if(nd.isAnEntity()){
			if(existsNoun(currentNodeValue)){
				int index = getNounElementIndex(currentNodeValue);
				ArrayList<String> edgeList= nd.getEdgeList();
				ArrayList<Node> children = nd.getChildren();
				for(int i=0;i<children.size();i++){
					if(edgeList.get(i).equals("trait")){		
						if(!semanticRoles.get(index).traits.contains(splitNodevalue(children.get(i).getValue()))){	
							semanticRoles.get(index).traits.add(splitNodevalue(children.get(i).getValue()));
							roleCount++;
						}
					}
					else if(edgeList.get(i).equals("semantic_role")){		
						if(!semanticRoles.get(index).semanticRoles.contains(children.get(i).getValue().substring(1, children.get(i).getValue().length()))){  
							semanticRoles.get(index).semanticRoles.add(children.get(i).getValue().substring(1, children.get(i).getValue().length()));
							roleCount++;
						}
					}
				}
			}
			
			else{
				SemanticRoleMatchData srTemp = new SemanticRoleMatchData();
				boolean flag = false;
				srTemp.noun = currentNodeValue;
				ArrayList<String> edgeList= nd.getEdgeList();
				ArrayList<Node> children = nd.getChildren();
				for(int i=0;i<children.size();i++){
					if(edgeList.get(i).equals("trait")){
						if(!srTemp.traits.contains(splitNodevalue(children.get(i).getValue()))){	
							srTemp.traits.add(splitNodevalue(children.get(i).getValue())); 
							roleCount++;
							flag = true;
						}
					}
					else if(edgeList.get(i).equals("semantic_role")){
						if(!srTemp.semanticRoles.contains(children.get(i).getValue().substring(1, children.get(i).getValue().length()))){  
							srTemp.semanticRoles.add(children.get(i).getValue().substring(1, children.get(i).getValue().length())); 
							roleCount++;
							flag = true;
						}
					}
				}
				if(flag) semanticRoles.add(srTemp);
			}
		}
		else if(nd.isAnEvent()){
			ArrayList<String> edgeList= nd.getEdgeList();
			ArrayList<Node> children = nd.getChildren();
			ArrayList<Node> agents = new ArrayList<Node>();
			ArrayList<Node> recepients = new ArrayList<Node>();
			for(int i=0;i<children.size();i++){
				if(edgeList.get(i).equals("agent"))	{
					agents.add(children.get(i));
				}
				else if( edgeList.get(i).equals("recipient") || hm.get(children.get(i).getValue()).equals("NN") || hm.get(children.get(i).getValue()).equals("NNP") ){
					boolean flagTemp = false;
					for(int j=0;i<recepients.size();j++){
						if(splitNodevalue(recepients.get(j).getValue()).equals(splitNodevalue(children.get(i).getValue()))){
							flagTemp = true;
							break;
						}
					}	
					if(!flagTemp)	recepients.add(children.get(i));
				}
			}
			for(Node tempAgent: agents){
				String agentNodeValue = splitNodevalue(tempAgent.getValue());
				List<String> l1 = EventSimilarity.StanfordLemmatizer(currentNodeValue);
				for(Node tempRecepient: recepients){
					String recepientNodeValue = splitNodevalue(tempRecepient.getValue());
					if(existsNoun(agentNodeValue)){
						int index = getNounElementIndex(agentNodeValue);
						if(!semanticRoles.get(index).recepients.contains(recepientNodeValue)){
							semanticRoles.get(index).recepients.add(recepientNodeValue);
							roleCount++;
						}
						if(!semanticRoles.get(index).verbs.contains(l1.get(0))){
							semanticRoles.get(index).verbs.add(l1.get(0));
							roleCount++;
						}
					}
					else{
						SemanticRoleMatchData srTemp = new SemanticRoleMatchData();
						srTemp.noun = agentNodeValue;
						srTemp.recepients.add(recepientNodeValue);
						srTemp.verbs.add(l1.get(0));
						semanticRoles.add(srTemp);
						roleCount=+2;
					}
				}
			}
			for(int i=0;i<children.size();i++){
				//recursion
				if(children.get(i).isAnEntity() || children.get(i).isAnEvent())	semanticRoleExtraction(children.get(i));
			}
		}
	}

	private static String splitNodevalue(String value) {
		String[] currentNodeValueTokens = value.split("-");
		return currentNodeValueTokens[0];
	}

	private static int getNounElementIndex(String value) {
		int i=0;
		for(SemanticRoleMatchData temp: semanticRoles){
			if(temp.noun.equalsIgnoreCase(value)) return i;
			else{
				String[] tokens1 = temp.noun.split("_");
				for(int j=0;j<tokens1.length;j++){
					if(value.equalsIgnoreCase(tokens1[j])) return i;
				}
			}
			i++;
		}
		return -1;
	}

	private static boolean existsNoun(String value) {
		// TODO Auto-generated method stub
		for(SemanticRoleMatchData temp: semanticRoles){
			if(temp.noun.equalsIgnoreCase(value)) return true;
			else{
				
				String[] tokens1 = temp.noun.split("_");
				for(int i=0;i<tokens1.length;i++){
					if(value.equalsIgnoreCase(tokens1[i])) return true;
				}
			}
		}
		return false;
	}
	
}
