

## EasyBlog <- A Simple Social Network Service Prototype
---

<!-- TOC -->

- [EasyBlog <- A Simple Social Network Service Prototype](#easyblog---a-simple-social-network-service-prototype)
    - [Description Of Application](#description-of-application)
    - [API Page :](#api-page-)
    - [Technology stack](#technology-stack)
    - [The Schema For The Database (The Table Definitions)](#the-schema-for-the-database-the-table-definitions)
    - [Where The App Is Doing Create, Read, Update, and Delete ( CRUD )](#where-the-app-is-doing-create-read-update-and-delete--crud-)
    - [Entity Relationship Diagram (ERD) For The Database](#entity-relationship-diagram-erd-for-the-database)
    - [GIF Demonstration Of The Application](#gif-demonstration-of-the-application)
    - [Docker file](#docker-file)
    - [How To Deploy](#how-to-deploy)
    - [Team Members](#team-members)

<!-- /TOC -->

---
[![CocoaPods](https://img.shields.io/cocoapods/l/AFNetworking.svg)]()

[![](https://img.shields.io/badge/maven-v%204.0.0-green.svg)]()

[![](https://img.shields.io/badge/Progress-70%25-yellow.svg)]()

[![Code Climate](https://img.shields.io/codeclimate/issues/github/me-and/mdf.svg)]()

[![](https://img.shields.io/badge/author-Huiming%20|%20Dinghao%20|%20YuHan%20|%20YuJia-red.svg)]()

### Description Of Application

>  A social network prototype
>  Could Custom visual webpage & UI

- [x] Personal Blog Service (Has The Potential To Expand Into Social Applications)
- [x] Support Follow & UnFollow , Like & UnLike , etc 
- [x] Can Be More Convenient To Manage Blog Compared With Wordpress (Minimalism & Lightweight)
- [x] Support Markdown / HTML Format
- [ ] UI Design Is Similar To pinterest.com ( Example ) 
- [ ] Support GitHub Calendar HeatMap (Not Completed)

### API Page :

http://muresearch.missouri.edu/DEVELOPERS/huiming/Swagger%20UI.html


### Technology stack 
---
- `Java Spring Boot` 
- `Mybatis` (Object Relational Mapping / Persistence Framework) (Maybe Use Mybatis Redis Cache Later) 
- `HikariCP` 
- `Mysql`
- `Ajax` / `Jquery` / `HTML5` / `JSON`
- `Kafka`
- `Echart.js`
- `D3.js`
- `BootStrap`
- `Docker`
- `Swagger`

### The Schema For The Database (The Table Definitions)
---

1. **User**      
```sql
"  CREATE TABLE `easyblog`.`user` (\r\n" +
"  `id` VARCHAR(100) NOT NULL,\r\n" +
"  `type` VARCHAR(45) NOT NULL,\r\n" + 
"  `gender` VARCHAR(45) NOT NULL,\r\n" + 
"  `username` VARCHAR(45) NOT NULL,\r\n" + 
"  `fullname` VARCHAR(45) NOT NULL,\r\n" + 
"  `firstname` VARCHAR(45) NOT NULL,\r\n" + 
"  `lastname` VARCHAR(45) NOT NULL,\r\n" + 
"  `email` VARCHAR(45) NOT NULL,\r\n" + 
"  `phonenumber` VARCHAR(45) NOT NULL,\r\n" + 
"  `gplusurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `domainurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `twitterurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `facebookurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `createdat` BIGINT UNSIGNED NOT NULL,\r\n" + 
"  `country` VARCHAR(45) NOT NULL,\r\n" + 
"  `imagemediumurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `imagesmallurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `imagelargeurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `posts` INTEGER UNSIGNED NOT NULL,\r\n" + 
"  `authid` VARCHAR(100) NOT NULL,\r\n" + 
"  `passwdmd5` VARCHAR(100) NOT NULL,\r\n" + 
"  PRIMARY KEY (`id`)\r\n" + 
")\r\n" + 
"ENGINE = InnoDB;")
```

2. **UnAuthUser**
```sql
"  CREATE TABLE `easyblog`.`user` (\r\n" +
"  `id` VARCHAR(100) NOT NULL,\r\n" +
"  `type` VARCHAR(45) NOT NULL,\r\n" + 
"  `gender` VARCHAR(45) NOT NULL,\r\n" + 
"  `username` VARCHAR(45) NOT NULL,\r\n" + 
"  `fullname` VARCHAR(45) NOT NULL,\r\n" + 
"  `firstname` VARCHAR(45) NOT NULL,\r\n" + 
"  `lastname` VARCHAR(45) NOT NULL,\r\n" + 
"  `email` VARCHAR(45) NOT NULL,\r\n" + 
"  `phonenumber` VARCHAR(45) NOT NULL,\r\n" + 
"  `gplusurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `domainurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `twitterurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `facebookurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `createdat` BIGINT UNSIGNED NOT NULL,\r\n" + 
"  `country` VARCHAR(45) NOT NULL,\r\n" + 
"  `imagemediumurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `imagesmallurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `imagelargeurl` VARCHAR(100) NOT NULL,\r\n" + 
"  `posts` INTEGER UNSIGNED NOT NULL,\r\n" + 
"  `authid` VARCHAR(100) NOT NULL,\r\n" + 
"  `passwdmd5` VARCHAR(100) NOT NULL,\r\n" + 
"  PRIMARY KEY (`id`)\r\n" + 
")\r\n" + 
"ENGINE = InnoDB;")
```

3. **Tag**
```sql
"CREATE TABLE `easyblog`.`tag` (\r\n" + 
"  `tagId` VARCHAR(100) NOT NULL,\r\n" + 
"  `tagName` VARCHAR(100) NOT NULL,\r\n" + 
"  `userPostBoardId` VARCHAR(100) NOT NULL,\r\n" +
"  `type` VARCHAR(100) NOT NULL,\r\n" + 
"  PRIMARY KEY (`tagId`)\r\n" + 
")\r\n" + 
"ENGINE = InnoDB;")
```

4. **Post**
```sql
"CREATE TABLE `easyblog`.`post` (\r\n" + 
"  `postId` VARCHAR(100) NOT NULL,\r\n" + 
"  `userId` VARCHAR(100) NOT NULL,\r\n" + 
"  `postName` VARCHAR(100) NOT NULL,\r\n" + 
"  `postTime` BIGINT UNSIGNED NOT NULL,\r\n" + 
"  `picNum` INTEGER NOT NULL,\r\n" + 
"  `isPrivate` BOOLEAN NOT NULL,\r\n" + 
"  `description` VARCHAR(100) NOT NULL,\r\n" + 
"  `url` VARCHAR(100) NOT NULL,\r\n" + 
"  PRIMARY KEY (`postId`)\r\n" + 
")\r\n" + 
"ENGINE = InnoDB;")
```

5. **Image**
```sql
"CREATE TABLE `easyblog`.`image` (\r\n" + 
"  `imageId` VARCHAR(100) NOT NULL,\r\n" + 
"  `imageType` VARCHAR(100) NOT NULL,\r\n" + 
"  `userPostBoardId` VARCHAR(100) NOT NULL,\r\n" + 
"  `url` VARCHAR(100) NOT NULL,\r\n" + 
"  PRIMARY KEY (`imageId`)\r\n" + 
")\r\n" + 
"ENGINE = InnoDB;")
```

6. **Board**
```sql
"CREATE TABLE `easyblog`.`board` (\r\n" + 
"  `boardId` VARCHAR(100) NOT NULL,\r\n" + 
"  `userId` VARCHAR(100) NOT NULL,\r\n" + 
"  `createdAt` BIGINT UNSIGNED NOT NULL,\r\n" + 
"  `boardName` VARCHAR(100) NOT NULL,\r\n" +  
"  `discription` VARCHAR(100) NOT NULL,\r\n" + 
"  `coverPic` VARCHAR(100) NOT NULL,\r\n" +  
"  `postNum` INTEGER NOT NULL,\r\n" + 
"  `isPrivate` BOOLEAN NOT NULL,\r\n" +
"  `url` VARCHAR(100) NOT NULL,\r\n" + 
"  PRIMARY KEY (`boardId`)\r\n" + 
")\r\n" + 
"ENGINE = InnoDB;")
```
7. **Behavior**
```sql
"CREATE TABLE `easyblog`.`behavior` (\r\n" + 
"  `behaviorId` VARCHAR(100) NOT NULL,\r\n" + 
"  `masterId` VARCHAR(100) NOT NULL,\r\n" + 
"  `slaveId` VARCHAR(100) NOT NULL,\r\n" + 
"  `behaviorType` VARCHAR(100) NOT NULL,\r\n" + 
"  `behaviorTime` BIGINT UNSIGNED NOT NULL,\r\n" + 
"  PRIMARY KEY (`behaviorId`)\r\n" + 
")\r\n" + 
"ENGINE = InnoDB;")
```

Meet with 1NF, 2NF, 3NF, BCNF requirements

### Where The App Is Doing Create, Read, Update, and Delete ( CRUD )
---

- **Create**  <- () {

    1. Create A *Non-Certified* `User`
    2. Create A Certified `User` By Verified Email Addresses 
    3. Create A New `Post` By A Certified User
    4. Create A New `Board` By A Certified User
    5. Create A New `Tag` To [ `Post` | `Board`] By A Certified `User`
    6. Create A New `Behavior` [ `Follow` | `UnFollow` | `Like` | `UnLike` | `Read` | `Get` | `.....`]
    7. . . .  etc 

    }

- **Read**    <- () {
    1. Read `User`'s Meta-Infomation
    2. Read `Post`'s Meta-Infomation
    3. Read `Board`'s Infomation
    4. Read `Post`'s Tags
    5. Read `User`'s Follow List
    6. Read `User`'s Like List
    7. . . . etc

    }
- **Update**  <- (){
    1. Update A Certified `User`'s Meta-Infomation : Such as, Avatar, UserName, FullName
    2. Update A `Post`'s Meta-Infomation : Such as , CoverImage , PostName
    3. Update A `Board`'s Meta-Infomation : Such as , CoverImage , BoardName
    4. . . . etc 
    }
- **Delete**  <- (){
    1. Delete A Certified `User`
    2. Delete A `Post`
    3. Delete A `Board`
    4. Delete A `Tag`
    5. . . . etc
    
    }

### Entity Relationship Diagram (ERD) For The Database
---
[![](https://github.com/bamboovir/EasyBlog_Pinterest/blob/master/image/E-R.png)]()

### GIF Demonstration Of The Application
---


### Docker file
---

### How To Deploy
---
- About application.properties

```
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/easyblog?autoReconnect=true&useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowMultiQueries=true
spring.datasource.username=root
spring.datasource.password=6666
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.http.multipart.max-file-size=30MB
spring.http.multipart.max-request-size=30MB
spring.datasource.type=com.zaxxer.hikari.HikariDataSource
logging.level.com.bamboovir.easyblog.mapper=trace
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=myGroup
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
```



### Team Members
---
- *Huiming Sun* 	(`Back-end` & `Front-end` )
- *Yuhan Chen* 	    (`Front-end` )
- *Hao Ding*		(`Front-end` )
- *Yujia Wang* 	    (`Front-end` )

----

Enjoy ( * _ * )
