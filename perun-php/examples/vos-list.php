<?php
include ("PerunRpcClient.php");

// CLIENT
$client = new PerunRpcClient();

// METHOD
$vos = $client->retrieveData("vosManager/getVos");

// ERROR WHILE DECODING RESPONSE
if($vos === null){
    print "Error while getting the VOs.";
    exit;
}

// PRINTING DATA
print "<h1>List of VOs</h1>";
print "<h2>Example</h2>";
print "<table>\n";
print "  <tr>\n";
print "    <th>ID</th>\n";
print "    <th>Short name</th>\n";
print "    <th>Name</th>\n";
print "  </tr>\n";

foreach($vos as $vo){
    print "  <tr>\n";
    print "    <td>{$vo -> id}</td>\n";
    print "    <td>{$vo -> shortName}</td>\n";
    print "    <td>{$vo -> name}</td>\n";
    print "  </tr>\n";
}

print "</table>\n";
