package database;

import java.util.ArrayList;

public class SemanticRoleMatchData {
	public String noun;
	public ArrayList<String> traits;
	public ArrayList<String> semanticRoles;
	public ArrayList<String> recepients;
	public ArrayList<String> relatedNouns;
	public ArrayList<String> verbs;
	
	public SemanticRoleMatchData(String noun, ArrayList<String> traits, ArrayList<String> semanticRoles, ArrayList<String> recepients, ArrayList<String> relatedNouns, ArrayList<String> verbs){
		this.noun =noun;
		this.traits = traits;
		this.semanticRoles = semanticRoles;
		this.recepients = recepients;
		this.relatedNouns = relatedNouns;
		this.verbs = verbs;
	}
	
	
	public SemanticRoleMatchData() {
		// TODO Auto-generated constructor stub
		this.traits = new ArrayList<String>();
		this.semanticRoles = new ArrayList<String>();
		this.recepients = new ArrayList<String>();
		this.relatedNouns = new ArrayList<String>();
		this.verbs = new ArrayList<String>();
	}


	public String getNoun(){
		return this.noun;
	}
	
	public ArrayList<String> getTraits(){
		return this.traits;
	}
	
	public ArrayList<String> getSemanticRoles(){
		return this.semanticRoles;
	}
	
	public ArrayList<String> getRecepients(){
		return this.recepients;
	}
	
	public ArrayList<String> getRelatedNouns(){
		return this.relatedNouns;
	}
	
	public ArrayList<String> getVerbs(){
		return this.verbs;
	}
	
	@Override
	public String toString(){
		String result = "";
		result = "NOUN: " + this.noun + " | TRAITS: " + this.traits.toString() + " | SEMANTIC ROLES: " + this.semanticRoles.toString() + " | RECEPIENTS: " + this.recepients.toString() + " | RELATED NOUNS: " + this.relatedNouns.toString() + " | VERBS: " + this.verbs.toString(); 
		return result;
	}
	
}
