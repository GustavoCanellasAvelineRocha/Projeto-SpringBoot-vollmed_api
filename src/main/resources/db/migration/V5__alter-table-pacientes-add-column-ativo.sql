ALTER TABLE pacientes ADD ativo tinyint;
UPDATE pacientes SET ativo = 1;