CREATE TABLE perfil (
                        id INTEGER PRIMARY KEY NOT NULL,
                        email VARCHAR(100) NOT NULL,
                        nomeCompleto VARCHAR(100),
                        usuario VARCHAR(100) NOT NULL,
                        dataNascimento VARCHAR(40),
                        senha VARCHAR(40),
                        level INTEGER NOT NULL,
                        exp INTEGER NOT NULL,
                        imgPerfil VARCHAR(300),
                        descricao VARCHAR(1300),
                        dataCriacao VARCHAR(40) NOT NULL,
                        premium VARCHAR(1) NOT NULL,
                        status VARCHAR(1) NOT NULL);



CREATE TABLE squad (
                       id INTEGER PRIMARY KEY NOT NULL,
                       nome VARCHAR(200) NOT NULL,
                       descricao VARCHAR(1000),
                       imgSquad VARCHAR(300),
                       area INTEGER,
                       curtidas INTEGER,
                       membros INTEGER,
                       dataCriacao VARCHAR(40) NOT NULL,
                       status VARCHAR(1) NOT NULL);


CREATE TABLE participante (
                              id INTEGER PRIMARY KEY NOT NULL,
                              idPerfil INTEGER NOT NULL,
                              participacao INTEGER,
                              funcao VARCHAR(100),
                              idSquad INTEGER NOT NULL,
                              dataEntrada VARCHAR(40) NOT NULL,
                              status VARCHAR(1) NOT NULL,
                              cargaHoraria INTEGER);

CREATE TABLE categoriaSquad (
                                id INTEGER PRIMARY KEY NOT NULL,
                                descricao VARCHAR(60))


CREATE TABLE historia (
                          id INTEGER PRIMARY KEY NOT NULL,
                          idparticipantereporter INTEGER,
                          idparticipanteassignee INTEGER,
                          nome varchar(150) NOT NULL,
                          descricao VARCHAR(1000),
                          finalData VARCHAR(40) ,
                          inicialData VARCHAR(40),
                          status integer);

CREATE TABLE tarefa (
                        id INTEGER PRIMARY KEY NOT NULL,
                        idHistoria INTEGER NOT NULL,
                        idparticipantereporter INTEGER,
                        idparticipanteassignee INTEGER,
                        nome VARCHAR(150) NOT NULL,
                        descricao VARCHAR(1000),
                        inicioData VARCHAR(40),
                        fimData VARCHAR(40),
                        status VARCHAR(1));

CREATE TABLE convite (
                         id INTEGER PRIMARY KEY NOT NULL,
                         idPerfilEnvio INTEGER NOT NULL,
                         idSquad INTEGER NOT NULL,
                         dataConvite VARCHAR(40) NOT NULL,
                         status VARCHAR(1) NOT NULL,
                         funcao VARCHAR(100) NOT NULL,
                         participacao VARCHAR(5) NOT NULL,
                         cargaHoraria INTEGER NOT NULL);

CREATE TABLE chat (
                      id INTEGER PRIMARY KEY NOT NULL,
                      idPerfil1 INTEGER NOT NULL,
                      idPerfil2 INTEGER NOT NULL);

CREATE TABLE mensagem (
                          id INTEGER PRIMARY KEY NOT NULL,
                          mensagem VARCHAR(1000) NOT NULL,
                          idChat INTEGER NOT NULL,
                          idPerfilEnvio INTEGER NOT NULL);

CREATE TABLE mensagemSquad (
                               id INTEGER PRIMARY KEY NOT NULL,
                               mensagem VARCHAR(1000) NOT NULL,
                               idParticipante  INTEGER NOT NULL,
                               dataEnvio VARCHAR(40) NOT NULL,
                               idSquad INTEGER NOT NULL
);

CREATE TABLE autenticacao (
                              id INTEGER PRIMARY KEY NOT NULL,
                              idPerfil INTEGER NOT NULL,
                              token VARCHAR(200) NOT NULL,
                              dataCriacao VARCHAR(40),
                              dataExpiracao VARCHAR(40));



CREATE TABLE publicacaoVaga (
                                id INTEGER PRIMARY KEY NOT NULL,
                                idPublicador INTEGER NOT NULL,
                                idSquad INTEGER NOT NULL,
                                descricao VARCHAR(1000),
                                nome VARCHAR(300) NOT NULL,
                                funcao VARCHAR(500),
                                participacao VARCHAR(5),
                                status VARCHAR(1),
                                atividades VARCHAR(1000),
                                cargaHoraria INTEGER);


CREATE TABLE inscricao (
                           id INTEGER PRIMARY KEY NOT NULL,
                           idPerfil INTEGER NOT NULL,
                           idPublicacao INTEGER NOT NULL,
                           mensagem VARCHAR(1000),
                           curriculo VARCHAR(1000),
                           status VARCHAR(1));


CREATE TABLE curtidasquad (
                              id INTEGER PRIMARY KEY NOT NULL,
                              idPerfil INTEGER NOT null,
                              idsquad INTEGER
);



ALTER TABLE tarefa
    ADD CONSTRAINT fk_idparticipantereporter FOREIGN KEY (idparticipantereporter) REFERENCES participante(id);

ALTER TABLE tarefa
    ADD CONSTRAINT fk_idparticipanteassignee FOREIGN KEY (idparticipanteassignee) REFERENCES participante(id);



ALTER TABLE historia
    ADD CONSTRAINT fk_idparticipantereporter FOREIGN KEY (idparticipantereporter) REFERENCES participante(id);

ALTER TABLE historia
    ADD CONSTRAINT fk_idparticipanteassignee FOREIGN KEY (idparticipanteassignee) REFERENCES participante(id);


ALTER TABLE participante ADD CONSTRAINT participante_idPerfil_perfil_id FOREIGN KEY (idPerfil) REFERENCES perfil(id);
ALTER TABLE participante ADD CONSTRAINT participante_idSquad_squad_id FOREIGN KEY (idSquad) REFERENCES squad(id);
ALTER TABLE historia ADD CONSTRAINT historia_idParticipante_participante_id FOREIGN KEY (idParticipante) REFERENCES participante(id);
ALTER TABLE tarefa ADD CONSTRAINT tarefa_idHistoria_historia_id FOREIGN KEY (idHistoria) REFERENCES historia(id);
ALTER TABLE convite ADD CONSTRAINT convite_idPerfilEnvio_perfil_id FOREIGN KEY (idPerfilEnvio) REFERENCES perfil(id);
ALTER TABLE convite ADD CONSTRAINT convite_idSquad_squad_id FOREIGN KEY (idSquad) REFERENCES squad(id);
ALTER TABLE chat ADD CONSTRAINT chat_idPerfil1_perfil_id FOREIGN KEY (idPerfil1) REFERENCES perfil(id);
ALTER TABLE chat ADD CONSTRAINT chat_idPerfil2_perfil_id FOREIGN KEY (idPerfil2) REFERENCES perfil(id);
ALTER TABLE mensagem ADD CONSTRAINT mensagem_idChat_chat_id FOREIGN KEY (idChat) REFERENCES chat(id);
ALTER TABLE mensagem ADD CONSTRAINT mensagem_idPerfilEnvio_perfil_id FOREIGN KEY (idPerfilEnvio) REFERENCES perfil(id);
ALTER TABLE mensagemSquad ADD CONSTRAINT mensagemSquad_idParticipante_participante_id FOREIGN KEY (idParticipante ) REFERENCES participante(id);
ALTER TABLE mensagemSquad ADD CONSTRAINT mensagemSquad_idSquad_squad_id FOREIGN KEY (idSquad) REFERENCES squad(id);
ALTER TABLE autenticacao ADD CONSTRAINT autenticacao_idPerfil_perfil_id FOREIGN KEY (idPerfil) REFERENCES perfil(id);
ALTER TABLE publicacaoVaga ADD CONSTRAINT publicacaoVaga_idPublicador_participante_id FOREIGN KEY (idPublicador) REFERENCES participante(id);
ALTER TABLE publicacaoVaga ADD CONSTRAINT publicacaoVaga_idSquad_squad_id FOREIGN KEY (idSquad) REFERENCES squad(id);
ALTER TABLE inscricao ADD CONSTRAINT inscricao_idPerfil_perfil_id FOREIGN KEY (idPerfil) REFERENCES perfil(id);
ALTER TABLE inscricao ADD CONSTRAINT inscricao_idPublicacao_publicacaoVaga_id FOREIGN KEY (idPublicacao) REFERENCES publicacaoVaga(id);
ALTER TABLE squad ADD CONSTRAINT squad_idcategoria FOREIGN KEY (area) REFERENCES categoriaSquad(id);