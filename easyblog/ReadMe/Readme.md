## EasyBlog 
---

[![CocoaPods](https://img.shields.io/cocoapods/l/AFNetworking.svg)]()

[![](https://img.shields.io/badge/maven-v%204.0.0-green.svg)]()

[![](https://img.shields.io/badge/develop%20Progress-70%-red.svg)]()

[![](https://img.shields.io/badge/author-Huiming%20|%20Dinghao%20|%20YuHan%20|%20YuJia-red.svg)]()

### Description Of Application

- [ ] UI Design Is Similar To pinterest.com (Not Completed)
- [x] Personal Blog Service (Has The Potential To Expand Into Social Applications)
- [x] Can Be More Convenient To Manage Blog Compared With Wordpress (Minimalism & Lightweight)
- [x] Support Markdown / HTML Format 
- [ ] Support GitHub Calendar HeatMap (Not Completed)

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

Meet with 1NF, 2NF, 3NF, BCNF requirements

### Where The App Is Doing Create, Read, Update, and Delete ( CRUD )
---



### Entity Relationship Diagram (ERD) For The Database
---

[![](https://github.com/bamboovir/EasyBlog_Pinterest/blob/master/image/E-R.png)]()

### GIF Demonstration Of The Application
---

### Docker file
---

### How To Deploy
---


### Team Members
---
- Huiming Sun 	(Back-end & Front-end )
- Yuhan Chen 	(Front-end: )
- Hao Ding		(Front-end: )
- Yujia Wang 	(Front-end: )

----

### Issue

<
1）登录后，把userid和sessionid保存到Memcached中
2）每次访问cytoscape页面时（不是访问login.html时），都①首先访问Memcached，如果Memcached中有Sessionid，则直接验证，②如果Memcached中没有，则返回到login.html
3）在login.html的后台登录过程中，如果login成功，则生成sessionid，并通过cookie返回给浏览器，同时，将userid & sessionid写入Memcached
>

<
使用Redis 完成 类似 twitter 的 订阅流功能
>

<
使用 Kafka , 完成 消息队列，进一步优化
>

### Other Things
enjoy ( * _ * ) -> Fork
