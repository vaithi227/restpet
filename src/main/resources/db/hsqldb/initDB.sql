CREATE TABLE programs (
  id INTEGER IDENTITY PRIMARY KEY ,
  program_name VARCHAR(30),
  buyer VARCHAR(30),
  dept VARCHAR(255),
  season VARCHAR(80),
  brand VARCHAR(20),
  cluster VARCHAR(20)
) ;
CREATE INDEX programs_program_name ON programs (program_name);
CREATE TABLE projections (
  id INTEGER IDENTITY PRIMARY KEY ,
  projection_name VARCHAR(30),
  program_name VARCHAR(30)
) ;
CREATE INDEX projections_projection_name ON projections (projection_name);