maven_mahout_template
=====================

### * Mahout javadoc ###

[https://builds.apache.org/job/Mahout-Quality/javadoc/]

### * org.apache.mahout.cf.taste.impl.model.mongodb.MongoDBDataModel ###
[http://archive.cloudera.com/cdh4/cdh/4/mahout/mahout-integration/index.html?org/apache/mahout/cf/taste/impl/model/mongodb/MongoDBDataModel.html]


### * git範例 UserCF ###
[http://blog.fens.me/hadoop-mahout-maven-eclipse/]
```bash
$ cd ~/test
$ git clone https://github.com/bsspirit/maven_mahout_template/tree/mahout-0.6
$ cd maven_mahout_template
$ mvn clean install #對應pom.xml
```

# Eclipse

## 執行範例 UserCF  

安裝m2e

```Help ➨ Eclipse MarketSpace ➨ Search ➨ maven integration```

導入project

```File ➨ Import ➨ Maven ➨ Existing Maven Project ➨ Browse ➨ ~/test/maven_mahout_template ```

加入jar

`mymahout ➨ Properties ➨ Java Build Path ➨ Libraries ➨ Add External Jars  ➨ `
`mongo-2.10.1.jar`

[https://github.com/downloads/mongodb/mongo-java-driver/mongo-2.10.1.jar]

```bash
mahout-core-0.9.jar
mahout-integration-0.9.jar
mahout-math-0.9.jar
```

[http://ftp.mirror.tw/pub/apache/mahout/0.9/mahout-distribution-0.9.tar.gz]

執行 UserCF
```bash
>src/main/java/
  >org.conan.mymahout.recommenddation
    >UserCF.java
```

結果 console
```bash
uid:1(104,4.274336)(106,4.000000)
uid:2(105,4.055916)
uid:3(103,3.360987)(102,2.773169)
uid:4(102,3.000000)
uid:5
```

# Mahout + mongoDB

### 準備mongo import 資料
```bash
$ cp ~/test/maven_mahout_template/datafile/item.csv ~/test/maven_mahout_template/datafile/itemTitle.csv
$ vi ~/test/maven_mahout_template/datafile/itemTitle.csv #加入headerline
user_id,item_id,preference
```
```csv
1,101,5.0
1,102,3.0
1,103,2.5
2,101,2.0
2,102,2.5
2,103,5.0
2,104,2.0
3,101,2.5
3,104,4.0
3,105,4.5
3,107,5.0
4,101,5.0
4,103,3.0
4,104,4.5
4,106,4.0
5,101,4.0
5,102,3.0
5,103,2.0
5,104,4.0
5,105,3.5
5,106,4.0
```
```bash
$ mongoimport --db test --collection item --type csv --headerline --file ~/test/maven_mahout_template/datafile/itemTitle.csv
```

### 確認資料
```bash
$ mongo
> use test
> db.item.find()
{ "_id" : ObjectId("54106dbeba569f7eeb59f043"), "user_id" : 1, "item_id" : 101, "preference" : 5,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f044"), "user_id" : 1, "item_id" : 102, "preference" : 3,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f045"), "user_id" : 1, "item_id" : 103, "preference" : 2.5, }
{ "_id" : ObjectId("54106dbeba569f7eeb59f046"), "user_id" : 2, "item_id" : 101, "preference" : 2,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f047"), "user_id" : 2, "item_id" : 102, "preference" : 2.5, }
{ "_id" : ObjectId("54106dbeba569f7eeb59f048"), "user_id" : 2, "item_id" : 103, "preference" : 5,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f049"), "user_id" : 2, "item_id" : 104, "preference" : 2,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f04a"), "user_id" : 3, "item_id" : 101, "preference" : 2.5, }
{ "_id" : ObjectId("54106dbeba569f7eeb59f04b"), "user_id" : 3, "item_id" : 104, "preference" : 4,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f04c"), "user_id" : 3, "item_id" : 105, "preference" : 4.5, }
{ "_id" : ObjectId("54106dbeba569f7eeb59f04d"), "user_id" : 3, "item_id" : 107, "preference" : 5,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f04e"), "user_id" : 4, "item_id" : 101, "preference" : 5,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f04f"), "user_id" : 4, "item_id" : 103, "preference" : 3,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f050"), "user_id" : 4, "item_id" : 104, "preference" : 4.5, }
{ "_id" : ObjectId("54106dbeba569f7eeb59f051"), "user_id" : 4, "item_id" : 106, "preference" : 4,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f052"), "user_id" : 5, "item_id" : 101, "preference" : 4,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f053"), "user_id" : 5, "item_id" : 102, "preference" : 3,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f054"), "user_id" : 5, "item_id" : 103, "preference" : 2,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f055"), "user_id" : 5, "item_id" : 104, "preference" : 4,      }
{ "_id" : ObjectId("54106dbeba569f7eeb59f056"), "user_id" : 5, "item_id" : 105, "preference" : 3.5, }
{ "_id" : ObjectId("54106dbfba569f7eeb59f057"), "user_id" : 5, "item_id" : 106, "preference" : 4,      }
```

###Mahout + mongoDB

執行mongoTest

新增`class ➨ mongoTest.java`

加入 MongoDBDataModel

`DataModel dbmodel = new MongoDBDataModel("localhost", 27017,"test", "item", true, true, null);`

執行 mongoTest
```bash
>src/main/java/
  >org.conan.mymahout.recommenddation
    >mongoTest.java
```

結果 console
```bash
uid:0(5,4.274336)(10,4.000000)
uid:4(7,4.055916)
uid:6(3,3.360987)(2,2.773169)
uid:9(2,3.000000)
uid:11
```

指令執行 `#不知為何不行???`
```bash
$ cd src/main/java/org/conan/mymahout/mongoTest.java
$ mvn exec:java -Dexec.mainClass="org.conan.mymahout.recommendation.UserCF.java"
```

執行UserCFmongo.java 實驗記錄  `//MongoDBDataModel 會重新編號???`
```
5 users
7 items
GenericItemPreferenceArray[itemID:1,{0=5.0,4=2.0,6=2.5,9=5.0,11=4.0}]
GenericItemPreferenceArray[itemID:2,{0=3.0,4=2.5,11=3.0}]
GenericItemPreferenceArray[itemID:3,{0=2.5,4=5.0,9=3.0,11=2.0}]
GenericItemPreferenceArray[itemID:5,{4=2.0,6=4.0,9=4.5,11=4.0}]
GenericItemPreferenceArray[itemID:7,{6=4.5,11=3.5}]
GenericItemPreferenceArray[itemID:8,{6=5.0}]
GenericItemPreferenceArray[itemID:10,{9=4.0,11=4.0}]
0:[1,2,3]
4:[1,2,3,5]
6:[1,5,7,8]
9:[1,3,5,10]
11:[1,2,3,5,7,10]
uid:0(5,4.274336)(10,4.000000)
uid:4(7,4.055916)
uid:6(3,3.360987)(2,2.773169)
uid:9(2,3.000000)
uid:11
```

### git fork版
[https://github.com/liyuqi/maven_mahout_template.git]

###內含程式碼
[https://github.com/liyuqi/maven_mahout_template/tree/master/src/main/java/org/conan/mymahout/recommendation]

	* UserCF.java
	* UserCFmongo.java
	* mongoTest.java

