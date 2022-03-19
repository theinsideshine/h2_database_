DROP TABLE IF EXISTS tasks;
CREATE TABLE tasks (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  description VARCHAR(250),
  status INTEGER(1),
  creation_date date
 );



DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  email VARCHAR(50),
  password VARCHAR(255),
  name VARCHAR(50),
  surname VARCHAR(50),
  phone VARCHAR(50),
  msj VARCHAR(255),
  creation_date date
 );

--! clave de pablo :jajeji
--! clave de test  :123456

 INSERT INTO users ( email, password, name, surname, phone, msj, creation_date) VALUES
 ( 'pablo.tavolaro@gmail.com', '$argon2i$v=19$m=1024,t=1,p=1$Mm95qVQZq+0Lgenl6XLwiQ$3vA2fikrUG32xekRD7Q7iYTVdrzWyutjYMZr6vmoFp4', 'Pablo Oscar', 'Tavolaro Ortiz', '115575-0792','Somos la vanguardia' , now()),
 ( 'test@gmail.com', '$argon2i$v=19$m=1024,t=1,p=1$ghMHLpB0CXkourZVpLC07g$JLKfZhdIpEkjP++bK+fuUvdN6Pycr7I5BuCQ317Xolo', 'QA', 'Prueba', '5555-4444','Amor por la calidad', now()),
 ( 'oscardelfino@hotmail.com', 'aVeces', 'Oscar', 'Delfino', '115565-4678','Somos los delfines', now()),
 ( 'jesusvillar@msn.com','steel', 'Rodrigo Jesus', 'Villar', '11405-2999','Somos lo visual', now()),
 ( 'jolea.depuch@gmail.com','pac', 'Alejo Martin', 'DePuch', '114565-4552','Somos vagueros', now());


INSERT INTO tasks (description,status,creation_date) VALUES
('buy cereal',0,now()),
('study math',0,now()),
('save money',1,now()),
('buy apples',0,now());

