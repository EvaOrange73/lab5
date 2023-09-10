create or replace function delete_trigger() returns trigger as
$delete_trigger$
begin
    delete from coordinates where old.coordinates = coordinates.id;
    delete from albums where old.best_album = albums.id;
return null;
end
$delete_trigger$ language plpgsql
;

create or replace trigger delete_trigger
    after delete on music_bands
    for each row execute procedure delete_trigger();