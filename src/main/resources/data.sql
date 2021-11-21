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

 INSERT INTO users ( email, password, name, surname, phone, msj, creation_date) VALUES
 ( 'pablo.tavolaro@gmail.com', 'entrada', 'Pablo Oscar', 'Tavolaro Ortiz', '115575-0681','Somos la vanguardia' , now()),
 ( 'oscardelfino@hotmail.com', 'salida', 'Oscar', 'Delfino', '115565-4678','Somos los delfines', now()),
 ( 'jesusvillar@msn.com','steel', 'Rodrigo Jesus', 'Villar', '11405-2999','Somos lo visual', now()),
 ( 'jolea.depuch@gmail.com','pac', 'Alejo Martin', 'DePuch', '114565-4552','Somos vagueros', now());


INSERT INTO tasks (description,status,creation_date) VALUES
('buy cereal',0,now()),
('study math',0,now()),
('save money',1,now()),
('buy apples',0,now());

