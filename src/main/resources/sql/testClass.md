selectByDTO
===

select id,name,status from TEST_CLASS as t
where 1 = 1 
@if(!isEmpty(name)){
    and t.name like #name#
@}
@if(!isEmpty(status)){
    t.status = #status#
@}
order by id desc

selectByPage
===

select 
@pageTag(){
    id,name,status
@} 
from TEST_CLASS as t
where 1 = 1 
@if(!isEmpty(name)){
    and t.name like #'%'+name+'%'#
@}
@if(!isEmpty(status)){
    and t.status = #status#
@}
@pageIgnoreTag(){
order by id desc
@}