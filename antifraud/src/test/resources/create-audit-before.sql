insert into anti_fraud.audit(id, entity_type, operation_type, created_by
    , modified_by, created_at, modified_at, new_entity_json, entity_json) values
    (1, 'entityTest', 'operationTest'
    , 'createdTest', 'modifiedTest'
    , '2001-01-01 01:01:01', '2001-01-01 01:01:01'
    , 'newJsonTest', 'JsonTest');