--
-- File generated with SQLiteStudio v3.3.3 on mar nov 22 17:52:59 2022
--
-- Text encoding used: System
--


-- Table: course
CREATE TABLE course (name VARCHAR PRIMARY KEY, img STRING, "dish-icon" STRING, "title-image" STRING);

UPDATE course
   SET title-image = 'view/style/img/course-title/antipasti-title.png'
 WHERE name = 'Antipasti';

UPDATE course
   SET title-image = 'view/style/img/course-title/primi-title.png'
 WHERE name = 'Primi';

UPDATE course
   SET title-image = 'view/style/img/course-title/secondi-title.png'
 WHERE name = 'Secondi';

UPDATE course
   SET title-image = 'view/style/img/course-title/contorni-title.png'
 WHERE name = 'Contorni';

UPDATE course
   SET title-image = 'view/style/img/course-title/dolci-title.png'
 WHERE name = 'Dolci';

UPDATE course
   SET title-image = 'view/style/img/course-title/bevande-title.png'
 WHERE name = 'Bevande';


