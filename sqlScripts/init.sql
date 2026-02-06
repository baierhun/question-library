CREATE TABLE question (
	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	question_text TEXT NOT NULL
);

CREATE TABLE answer_option (
	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	option_text TEXT NOT NULL,
	is_answer INTEGER NOT NULL CHECK (is_answer IN (0, 1)) DEFAULT 0,
	question_id INTEGER NOT NULL
);

CREATE TABLE question_tag (
	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	name TEXT NOT NULL UNIQUE
);

CREATE TABLE question_question_tag (
	question_id INTEGER NOT NULL,
	question_tag_id INTEGER NOT NULL,
	PRIMARY KEY (question_id, question_tag_id),
	CONSTRAINT question_question_tag_question_FK FOREIGN KEY (question_id) REFERENCES question(id),
	CONSTRAINT question_question_tag_question_tag_FK FOREIGN KEY (question_tag_id) REFERENCES question_tag(id)
);
