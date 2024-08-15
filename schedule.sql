-- auto-generated definition
create table schedule
(
    schedule_id  int auto_increment primary key,
    schedule     varchar(200) null,
    password     varchar(30)  not null,
    created_date datetime     null,
    updated_date datetime     null,
    assignee_id  int          null,
    constraint fk_assignee foreign key (assignee_id) references assignee (assignee_id)
);

