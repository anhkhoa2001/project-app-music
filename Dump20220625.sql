-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: dbmobile
-- ------------------------------------------------------
-- Server version	5.7.33-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation` (
  `id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `method` varchar(255) DEFAULT NULL,
  `upvotes` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
/*!40000 ALTER TABLE `donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music`
--

DROP TABLE IF EXISTS `music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `music` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) DEFAULT NULL,
  `likes` int(11) NOT NULL,
  `listens` int(11) NOT NULL,
  `music_tracks` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `singer` varchar(45) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music`
--

LOCK TABLES `music` WRITE;
/*!40000 ALTER TABLE `music` DISABLE KEYS */;
INSERT INTO `music` VALUES (1,'image/music/vn/con%20trai%20c%C6%B0ng.jpg',0,0,158,'Con trai cưng','Bray','sounds/vn/Bray - Con trai cưng.mp3'),(2,'image/music/vn/n%C6%B0%E1%BB%9Bc%20m%E1%BA%AFt%20em%20lau%20b%E1%BA%B1ng%20t%C3%ACnh%20y%C3%AAu%20m%E1%BB%9Bi.jpg',0,0,286,'Nước Mắt Em Lau Bằng Tình Yêu Mới','Da LAB','sounds/vn/Da LAB - Nước Mắt Em Lau Bằng Tình Yêu Mới.mp3'),(3,'image/music/vn/th%E1%BB%A9c%20gi%E1%BA%A5c.jpg',0,0,259,'Thức giấc','Da LAB','sounds/vn/DaLab - Thức giấc.mp3'),(4,'image/music/vn/c%C3%B3%20nh%C6%B0%20kh%C3%B4ng%20c%C3%B3.jpg',0,0,270,'Có như không có','Hiền Hồ','sounds/vn/Hiền Hồ - Có như không có.mp3'),(5,'image/music/vn/g%E1%BA%B7p%20nh%C6%B0ng%20kh%C3%B4ng%20%E1%BB%9F%20l%E1%BA%A1i.jpg',0,0,282,'Gặp nhưng không ở lại','Hiền Hồ','sounds/vn/Hiền Hồ - Gặp nhưng không ở lại.mp3'),(6,'image/music/vn/r%E1%BB%93i%20ng%C6%B0%E1%BB%9Di%20th%C6%B0%C6%A1ng%20c%C5%A9ng%20h%C3%B3a%20ng%C6%B0%E1%BB%9Di%20d%C6%B0ng.jpg',0,0,278,'Rồi người thương cũng hóa người dưng','Hiền Hồ','sounds/vn/Hiền Hồ - Rồi người thương cũng hóa người dưng.mp3'),(7,'image/music/vn/b%E1%BA%A1c%20ph%E1%BA%ADn.jpg',0,0,249,'Bạc phận','KICM&JACK','sounds/vn/KICM&JACK - Bạc phận.mp3'),(8,'image/music/vn/em%20g%C3%AC%20%C6%A1i.jpg',0,0,246,'Em gì ơi','KICM&JACK','sounds/vn/KICM&JACK - Em gì ơi.mp3'),(9,'image/music/vn/s%C3%B3ng%20gi%C3%B3.jpg',0,0,254,'Sóng gió','KICM&JACK','sounds/vn/KICM&JACK - Sóng gió.mp3'),(10,'image/music/vn/m%E1%BB%99t%20b%C6%B0%E1%BB%9Bc%20y%C3%AAu%20v%E1%BA%A1n%20d%E1%BA%B7m%20%C4%91au.jpg',0,0,299,'Một bước yêu vạn dặm đau','Mr Siro','sounds/vn/Mr Siro - Một bước yêu vạn dặm đau.mp3'),(11,'image/music/vn/anh%20l%C3%A0%20ai.jpg',0,0,262,'Anh là ai','Phương Ly','sounds/vn/Phương Ly - Anh là ai.mp3'),(12,'image/music/vn/th%E1%BA%B1ng%20%C4%91i%C3%AAn.jpg',0,0,287,'Thằng điên','Phương Ly&Justatee','sounds/vn/Phương Ly&Justatee - Thằng điên.mp3'),(13,'image/music/vn/ai%20l%C3%A0%20ng%C6%B0%E1%BB%9Di%20th%C6%B0%C6%A1ng%20em.jpg',0,0,307,'Ai là người thương em','Quân AP','sounds/vn/Quân AP - Ai là người thương em.mp3'),(14,'image/music/vn/b%C3%B4ng%20hoa%20%C4%91%E1%BA%B9p%20nh%E1%BA%A5t.jpg',0,0,316,'Bông hoa đẹp nhất','Quân AP','sounds/vn/QuânAP - Bông hoa đẹp nhất.mp3'),(15,'image/music/vn/n%E1%BA%BFu%20ng%C3%A0y%20%E1%BA%A5y.jpg',0,0,293,'Nếu ngày ấy','Soobin Hoàng sơn','sounds/vn/Soobin Hoàng sơn - Nếu ngày ấy.mp3'),(16,'image/music/vn/h%C3%A3y%20trao%20cho%20anh.jpg',0,0,245,'Hãy trao cho anh','Sơn Tùng MTP','sounds/vn/Sơn Tùng MTP - Hãy trao cho anh.mp3'),(17,'image/music/vn/ch%E1%BA%A1y%20ngay%20%C4%91i.jpg',0,0,248,'Chạy ngay đi','Sơn Tùng MTP','sounds/vn/SơnTùngMTP - Chạy ngay đi.mp3'),(18,'image/music/vn/th%E1%BA%AFc%20m%E1%BA%AFc.jpg',0,0,206,'Thắc mắc','Thịnh Suy','sounds/vn/Thịnh Suy - Thắc mắc.mp3'),(19,'image/music/vn/anh%20%C4%91%E1%BA%BFch%20c%E1%BA%A7n%20g%C3%AC%20nhi%E1%BB%81u%20ngo%C3%A0i%20em.jpg',0,0,217,'Anh Đếch Cần Gì Nhiều Ngoài Em','Đen','sounds/vn/Đen - Anh Đếch Cần Gì Nhiều Ngoài Em.mp3'),(20,'image/music/vn/b%C3%A0i%20n%C3%A0y%20chill%20ph%E1%BA%BFt.jpg',0,0,276,'Bài Này Chill Phết','Đen','sounds/vn/Đen - Bài Này Chill Phết.mp3'),(21,'image/music/vn/hai%20tri%E1%BB%87u%20n%C4%83m.jpg',0,0,217,'hai triệu năm','Đen','sounds/vn/Đen - hai triệu năm.mp3'),(22,'image/music/vn/l%E1%BB%91i%20nh%E1%BB%8F.jpg',0,0,253,'Lối Nhỏ','Đen','sounds/vn/Đen - Lối Nhỏ.mp3'),(23,'image/music/vn/m%E1%BB%99t%20tri%E1%BB%87u%20like.jpg',0,0,263,'một triệu like','Đen','sounds/vn/Đen - một triệu like.mp3'),(24,'image/music/vn/ng%C3%A0y%20kh%C3%A1c%20l%E1%BA%A1.jpg',0,0,212,'Ngày Khác Lạ','Đen','sounds/vn/Đen - Ngày Khác Lạ.mp3'),(25,'image/music/vn/ta%20c%E1%BB%A9%20%C4%91i%20c%C3%B9ng%20nhau.jpg',0,0,347,'Ta Cứ Đi Cùng Nhau','Đen','sounds/vn/Đen - Ta Cứ Đi Cùng Nhau.mp3'),(26,'image/music/vn/%C4%91i%20v%E1%BB%81%20nh%C3%A0.jpg',0,0,200,'Đi Về Nhà','Đen&Justatee','sounds/vn/Đen x JustaTee - Đi Về Nhà.mp3'),(27,'image/music/vn/kh%C3%B3%20v%E1%BA%BD%20n%E1%BB%A5%20c%C6%B0%E1%BB%9Di.jpg',0,0,333,'Khó vẽ nụ cười','ĐạtG','sounds/vn/ĐạtG - Khó vẽ nụ cười.mp3'),(28,'image/music/vn/th%C3%AAm%20bao%20nhi%C3%AAu%20l%C3%A2u.jpg',0,0,303,'Thêm Bao Nhiêu Lâu','ĐạtG','sounds/vn/ĐạtG - Thêm Bao Nhiêu Lâu.mp3'),(29,'image/music/vn/b%C3%A1nh%20m%C3%AC%20kh%C3%B4ng.jpg',0,0,272,'Bánh mì không','ĐạtG&Du Uyên','sounds/vn/ĐạtG&DuUyên - Bánh mì không.mp3'),(30,'image/music/vn/ta%20c%C3%B2n%20y%C3%AAu%20nhau.jpg',0,0,212,'Ta còn yêu nhau','Đức Phúc','sounds/vn/Đức Phúc - Ta còn yêu nhau.mp3'),(31,'image/music/vn/%C3%A1nh%20n%E1%BA%AFng%20c%E1%BB%A7a%20anh.jpg',0,0,265,'Ánh nắng của anh','Đức Phúc','sounds/vn/Đức Phúc - Ánh nắng của anh.mp3'),(32,'image/music/kr/bang%20bang%20bang.jpg',0,0,220,'BANG BANG BANG','Big Bang','sounds/kr/Big Bang - BANG BANG BANG.mp3'),(33,'image/music/kr/fantastic%20baby.jpg',0,0,235,'Fantastic Baby','Big Bang','sounds/kr/Big Bang - Fantastic Baby.mp3'),(34,'image/music/kr/haru%20haru.jpg',0,0,244,'Haru Haru','Big Bang','sounds/kr/Big Bang - Haru Haru.mp3'),(35,'image/music/kr/if%20you.jpg',0,0,264,'IF YOU','Big Bang','sounds/kr/Big Bang - IF YOU.mp3'),(36,'image/music/kr/loser.jpg',0,0,219,'Loser','Big Bang','sounds/kr/Big Bang - Loser.mp3'),(37,'image/music/kr/boombayah.jpg',0,0,245,'BOOMBAYAH','BLACKPINK','sounds/kr/BLACKPINK - BOOMBAYAH.mp3'),(38,'image/music/kr/kill%20this%20love.jpg',0,0,186,'Kill This Love','BLACKPINK','sounds/kr/BLACKPINK - Kill This Love.mp3'),(39,'image/music/kr/stay.jpg',0,0,230,'STAY','BLACKPINK','sounds/kr/BLACKPINK - STAY.mp3'),(40,'image/music/kr/stay%20with%20me.jpg',0,0,191,'Stay With Me','ChanYeol','sounds/kr/ChanYeol - Stay With Me.mp3'),(41,'image/music/kr/bbibbi.jpg',0,0,194,'BBIBBI','IU','sounds/kr/IU - BBIBBI.mp3'),(42,'image/music/kr/blueming.jpg',0,0,217,'Blueming','IU','sounds/kr/IU - Blueming.mp3'),(43,'image/music/kr/celebrity.jpg',0,0,196,'Celebrity','IU','sounds/kr/IU - Celebrity.mp3'),(44,'image/music/kr/eight.jpg',0,0,167,'Eight','IU','sounds/kr/IU - Eight.mp3'),(45,'image/music/kr/palette.jpg',0,0,218,'Palette','IU&G-DRAGON','sounds/kr/IU&G-DRAGON - Palette.mp3'),(46,'image/music/kr/way%20back%20home.jpg',0,0,214,'Way back home','Shaun','sounds/kr/Shaun - Way back home.mp3'),(47,'image/music/us-uk/2002.jpg',0,0,187,'2002','Anne-Marie','sounds/us-uk/Anne-Marie - 2002.mp3'),(48,'image/music/us-uk/leave%20the%20door%20open.jpg',0,0,242,'Leave The Door Open','Bruno Mars&Anderson Paak&Silk Sonic','sounds/us-uk/Bruno Mars&Anderson Paak&Silk Sonic - Leave The Door Open.mp3'),(49,'image/music/us-uk/havana.jpg',0,0,213,'Havana','Camila Cabello','sounds/us-uk/Camila Cabello - Havana.mp3'),(50,'image/music/us-uk/attention.jpg',0,0,212,'Attention','Charlie Puth','sounds/us-uk/Charlie Puth - Attention.mp3'),(51,'image/music/us-uk/one%20call%20away.jpg',0,0,194,'One Call Away','Charlie Puth','sounds/us-uk/Charlie Puth - One Call Away.mp3'),(52,'image/music/us-uk/see%20you%20again.jpg',0,0,230,'See You Again','Charlie Puth&Wiz Khalifa','sounds/us-uk/CharliePuth&Wiz Khalifa - See You Again.mp3'),(53,'image/music/us-uk/shape%20of%20you.jpg',0,0,234,'Shape Of You','Ed Sheeran','sounds/us-uk/Ed Sheeran - Shape Of You.mp3'),(54,'image/music/us-uk/comethru.jpg',0,0,0,'comethru','Jeremy Zucker','sounds/us-uk/Jeremy Zucker - comethru.mp3'),(55,'image/music/us-uk/baby.jpg',0,0,210,'Baby','Justin Bieber','sounds/us-uk/Justin Bieber - Baby.mp3'),(56,'image/music/us-uk/what%20do%20you%20mean.jpg',0,0,205,'What Do You Mean','Justin Bieber','sounds/us-uk/Justin Bieber - What Do You Mean.mp3'),(57,'image/music/us-uk/animals.jpg',0,0,229,'Animals','Maroon5','sounds/us-uk/Maroon5 - Animals.mp3'),(58,'image/music/us-uk/memories.jpg',0,0,189,'Memories','Maroon5','sounds/us-uk/Maroon5 - Memories.mp3'),(59,'image/music/us-uk/sugar.jpg',0,0,236,'Sugar','Maroon5','sounds/us-uk/Maroon5 - Sugar.mp3'),(60,'image/music/us-uk/girls%20like%20you.jpg',0,0,236,'Girls Like You','Maroon5&CardiB','sounds/us-uk/Maroon5&CardiB- - Girls Like You.mp3'),(61,'image/music/us-uk/at%20my%20worst.jpg',0,0,170,'At My Worst','Pink Sweat$','sounds/us-uk/Pink Sweat$ - At My Worst.mp3'),(62,'image/music/us-uk/we%20don\'t%20talk%20anymore.jpg',0,0,218,'We Don\'t Talk Anymore','Selena Gomez&Charlie Puth','sounds/us-uk/Selena Gomez&Charlie Puth - We Don\'t Talk Anymore.mp3'),(63,'image/music/us-uk/senorita.jpg',0,0,0,'senorita','Shawn Mendes&Camila Cabello','sounds/us-uk/Shawn Mendes&Camila Cabello - Señorita.mp3'),(64,'image/music/us-uk/blank%20space.jpg',0,0,232,'Blank Space','Taylor Swift','sounds/us-uk/Taylor Swift - Blank Space.mp3'),(65,'image/music/us-uk/don\'t%20let%20me%20down.jpg',0,0,208,'Don\'t Let Me Down','The Chainsmokers','sounds/us-uk/The Chainsmokers - Don\'t Let Me Down.mp3'),(66,'image/music/us-uk/some%20thing%20just%20like%20this.jpg',0,0,244,'Something Just Like This','The Chainsmokers','sounds/us-uk/The Chainsmokers - Something Just Like This.mp3');
/*!40000 ALTER TABLE `music` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music_like`
--

DROP TABLE IF EXISTS `music_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `music_like` (
  `id_music` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  KEY `FK6a2xd9nrqpf4wqipx5f14fkfw` (`id_user`),
  KEY `FKdyh4yeu814ir1tqyj4eg7d52j` (`id_music`),
  CONSTRAINT `FK6a2xd9nrqpf4wqipx5f14fkfw` FOREIGN KEY (`id_user`) REFERENCES `music` (`id`),
  CONSTRAINT `FKdyh4yeu814ir1tqyj4eg7d52j` FOREIGN KEY (`id_music`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music_like`
--

LOCK TABLES `music_like` WRITE;
/*!40000 ALTER TABLE `music_like` DISABLE KEYS */;
INSERT INTO `music_like` VALUES (1,1),(1,3),(1,36),(1,20),(1,2),(1,7),(2,5),(2,7),(2,1),(1,63),(1,6),(1,8),(1,16);
/*!40000 ALTER TABLE `music_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music_musicgenre`
--

DROP TABLE IF EXISTS `music_musicgenre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `music_musicgenre` (
  `music_genre_id` int(11) NOT NULL,
  `music_id` int(11) NOT NULL,
  KEY `FKdk7btb56csr0c85wesnftdxhm` (`music_id`),
  KEY `FKs18p8496r74xhiu2xqm0uk5c6` (`music_genre_id`),
  CONSTRAINT `FKdk7btb56csr0c85wesnftdxhm` FOREIGN KEY (`music_id`) REFERENCES `musicgenre` (`id`),
  CONSTRAINT `FKs18p8496r74xhiu2xqm0uk5c6` FOREIGN KEY (`music_genre_id`) REFERENCES `music` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music_musicgenre`
--

LOCK TABLES `music_musicgenre` WRITE;
/*!40000 ALTER TABLE `music_musicgenre` DISABLE KEYS */;
INSERT INTO `music_musicgenre` VALUES (1,4),(1,2),(2,4),(2,3),(3,4),(3,1),(4,4),(4,3),(5,4),(5,1),(6,4),(6,1),(7,4),(7,3),(8,4),(8,1),(9,4),(9,2),(10,4),(10,1),(11,4),(11,3),(12,4),(12,2),(13,4),(13,3),(14,4),(14,1),(15,4),(15,2),(16,4),(16,1),(17,4),(17,1),(18,4),(18,1),(19,4),(19,3),(20,4),(20,1),(21,4),(21,2),(22,4),(22,2),(23,4),(23,2),(24,4),(24,3),(25,4),(25,3),(26,4),(26,3),(27,4),(27,2),(28,4),(28,2),(29,4),(29,2),(30,4),(30,2),(31,4),(31,2),(32,5),(32,1),(33,5),(33,3),(34,5),(34,1),(35,5),(35,1),(36,5),(36,1),(37,5),(37,2),(38,5),(38,1),(39,5),(39,2),(40,5),(40,2),(41,5),(41,3),(42,5),(42,3),(43,5),(43,2),(44,5),(44,1),(45,5),(45,2),(46,6),(46,3),(47,6),(47,1),(48,6),(48,1),(49,6),(49,2),(50,6),(50,2),(51,6),(51,2),(52,6),(52,1),(53,6),(53,1),(54,6),(54,2),(55,6),(55,3),(56,6),(56,3),(57,6),(57,2),(58,6),(58,2),(59,6),(59,3),(60,6),(60,2),(61,6),(61,1),(62,6),(62,3),(63,6),(63,2),(64,6),(64,3),(65,6),(65,3),(66,6),(66,3);
/*!40000 ALTER TABLE `music_musicgenre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `music_singer`
--

DROP TABLE IF EXISTS `music_singer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `music_singer` (
  `singer_id` int(11) NOT NULL,
  `music_id` int(11) NOT NULL,
  KEY `FKq79ne0nbyibs6g6iy7ov6d4ah` (`music_id`),
  KEY `FKc2nwpgr8pcixfbu58xutdfho6` (`singer_id`),
  CONSTRAINT `FKc2nwpgr8pcixfbu58xutdfho6` FOREIGN KEY (`singer_id`) REFERENCES `music` (`id`),
  CONSTRAINT `FKq79ne0nbyibs6g6iy7ov6d4ah` FOREIGN KEY (`music_id`) REFERENCES `singer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music_singer`
--

LOCK TABLES `music_singer` WRITE;
/*!40000 ALTER TABLE `music_singer` DISABLE KEYS */;
INSERT INTO `music_singer` VALUES (53,1),(12,2),(26,2),(64,3),(50,4),(51,4),(52,4),(62,4),(54,5),(55,6),(56,6),(47,7),(60,8),(27,9),(28,9),(29,9),(45,10),(40,11),(48,12),(61,13),(16,14),(17,14),(2,15),(3,15),(48,16),(37,17),(38,17),(39,17),(10,18),(13,19),(14,19),(62,20),(48,21),(30,22),(31,22),(57,23),(58,23),(59,23),(60,23),(18,24),(46,25),(7,26),(8,26),(9,26),(52,27),(41,28),(42,28),(43,28),(44,28),(45,28),(63,29),(29,30),(49,31),(63,31),(15,32),(65,33),(66,33),(1,34),(11,35),(12,35),(32,36),(33,36),(34,36),(35,36),(36,36),(4,37),(5,37),(6,37),(7,38),(8,38),(9,38),(19,39),(20,39),(21,39),(22,39),(23,39),(24,39),(25,39),(26,39);
/*!40000 ALTER TABLE `music_singer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `musicgenre`
--

DROP TABLE IF EXISTS `musicgenre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `musicgenre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `slogan` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `musicgenre`
--

LOCK TABLES `musicgenre` WRITE;
/*!40000 ALTER TABLE `musicgenre` DISABLE KEYS */;
INSERT INTO `musicgenre` VALUES (1,'Pop','image/genre/pop.jpg','One thing I do love about these dark winter mornings and evenings is getting to see the stars more!'),(2,'Rap','image/genre/rap.jpg','Don’t tell me what do you think about that I want to hear.Tell me the truth'),(3,'R&B Soul','image/genre/r&b.jpg','It was very interesting in my world, because I grew up as a fan and I did not know that there was a thing called R&B, pop, country, classical - I just knew that I loved music'),(4,'V-Pop','image/genre/vpop.jpg','A simple “bye” can make us cry ,a simple “joke” can make us laugh, and a simple ” care” can make us fall in love'),(5,'K-Pop','image/genre/kpop.jpg','Let\'s Go Together, Even If It\'s Not a Road of Flowers, Let\'s Walk Together, Let\'s See Each Other For a Long Time'),(6,'US-UK','image/genre/us-uk.jpg',NULL);
/*!40000 ALTER TABLE `musicgenre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `singer`
--

DROP TABLE IF EXISTS `singer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `singer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `singer`
--

LOCK TABLES `singer` WRITE;
/*!40000 ALTER TABLE `singer` DISABLE KEYS */;
INSERT INTO `singer` VALUES (1,'Ed Sheeran'),(2,'Justatee'),(3,'Taylor Swift'),(4,'Charlie Puth'),(5,'Jeremy Zucker'),(6,'Justin Bieber'),(7,'Anne-Marie'),(8,'CardiB'),(9,'ĐạtG'),(10,'G-DRAGON'),(11,'ChanYeol'),(12,'Anderson Paak'),(13,'Pink Sweat$'),(14,'Sơn Tùng MTP'),(15,'Da LAB'),(16,'Silk Sonic'),(17,'BLACKPINK'),(18,'Mr Siro'),(19,'Quân AP'),(20,'Selena Gomez'),(21,'Bruno Mars'),(22,'Đức Phúc'),(23,'Maroon5'),(24,'Thịnh Suy'),(25,'Shaun'),(26,'KICM'),(27,'Wiz Khalifa'),(28,'IU'),(29,'Shawn Mendes'),(30,'Du Uyên'),(31,'Camila Cabello'),(32,'Soobin Hoàng sơn'),(33,'The Chainsmokers'),(34,'Bray'),(35,'Phương Ly'),(36,'Big Bang'),(37,'Hiền Hồ'),(38,'JACK'),(39,'Đen');
/*!40000 ALTER TABLE `singer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'khoadamtam','Dam Tam Khoa','1','0981882815',NULL,'admin'),(2,'khoa@gmail.com','Khoa Dam Tam ','1','0981882815',NULL,'admin2'),(3,'kho1','Khoa dam tam','1','0981882815',NULL,'admin3');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-25 15:43:49
