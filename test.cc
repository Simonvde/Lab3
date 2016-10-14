#include<iostream>
#include<vector>
#include<string>
#include<algorithm>


using namespace std;

int main () {
	int n_vertices, n_edges;
	cin >> n_vertices >> n_edges;
	edgelist = vector<vector<string>> (n_vertices);
	int j=0;
	string last_word;
	for (int i=0; i<n_edges; ++i){
		string s1,s2;
		cin >> s1 >> s2;
		if (j==0) {
			edgelist[j].push_back(s1);
			edgelist[j].push_back(s2);
		}
		else if (s1!=last_word) {
			++j;
			edgelist[j].push_back(s1);
			edgelist[j].push_back(s2);
		}
		else{
			edgelist[j].push_back(s2);
		}
		last_word=s1;
	}
	for (int i=0; i<n_edges; ++i){
		string s1,s2;
		cin >> s1 >> s2;
		
	}
	
}
