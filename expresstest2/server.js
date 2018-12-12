//zum ausführen erst npm install
//dann node server.js

var express        =         require("express");
var bodyParser     =         require("body-parser");
var net = require('net');
var app            =         express();
var request = require("request")
var http = require('http'),
    fs = require('fs');
  var parseString = require("xml2js").parseString;
  

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.get('/',function(req,res){
  res.sendfile("index.html");
});



app.post('/neuesSpiel',function(req,res){





//Parse XML to JSON
request('http://www.game-engineering.de:8080/rest/schach/spiel/admin/neuesSpiel/5', function(err, res, body) {
  console.log(body)

    parseString(body, (error, result) => {
  if (error) {
    console.log("ERRRROR: " + error);
    return;
  }
  console.log(JSON.stringify(result));
});
});



  res.end("yes");
});
app.listen(3000,function(){
  console.log("Started on PORT 3000");
})
