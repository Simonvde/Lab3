//
//  main.cpp
//  Lab3
//
//  Created by Simon Van den Eynde on 14/10/16.
//  Copyright © 2016 Simon Van den Eynde. All rights reserved.
//

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <sstream>
using namespace std;

vector<vector<string>> read(string filename);

int main () {
    read("Testdata.txt");
    
    
    
    return 0;
}


string* spaceSplitPair(string pair){
    string delimiter = " ";
    size_t pos = pair.find(delimiter);
    string v1 = pair.substr(0, pair.find(delimiter));
    pair.erase(0, pos + delimiter.length());
    string v2 = pair;
    
    cout << v1<<endl;
    
    string s[]{v1,v2};

    return s;
}

vector<vector<string>> read(string filename) {
    //string vertices[][];
    string line;
    ifstream myfile ("Testdata.txt");
    if (myfile.is_open())
    {
        
        while ( getline (myfile,line) )
        {
            string *edge=spaceSplitPair(line);
            
            cout << edge[0] << endl;
            
        }
        myfile.close();
    }
    
    else cout << "Unable to open file";
    
    
    int n_vertices, n_edges;
    cin >> n_vertices >> n_edges;
    vector<vector<string>> edgelist = vector<vector<string>>(n_vertices);
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
    
    for(int i=0; i<n_edges; i++){
        string s = "";
        for(int j=0; j<edgelist[i].size(); j++){
            s += edgelist[i][j];
        }
        cout << s;
    }
    
    return edgelist;
    
}

