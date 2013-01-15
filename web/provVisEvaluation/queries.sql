--NLG Intro
SELECT nlg.* FROM ourspaces3.log_nlgRequest nlg join ourspaces3.log_provviseval e on (nlg.userid = e.userid)
where 
nlg.cur_timestamp < e.cur_timestamp 
and vis = 'nlgintro';

--NLG from NLG Task
SELECT distinct nlg.* FROM ourspaces3.log_nlgRequest nlg join ourspaces3.log_provviseval e on (nlg.userid = e.userid)
join ourspaces3.log_provviseval e2 on (nlg.userid = e.userid)
where 
nlg.cur_timestamp >= e.cur_timestamp 
and nlg.cur_timestamp <= e2.cur_timestamp 
and e.vis = 'nlgintro'
and e2.vis = 'NLG';

--NLG from Combined
SELECT distinct nlg.* FROM ourspaces3.log_nlgRequest nlg join ourspaces3.log_provviseval e on (nlg.userid = e.userid)
join ourspaces3.log_provviseval e2 on (nlg.userid = e.userid)
where 
nlg.cur_timestamp >= e.cur_timestamp 
and nlg.cur_timestamp <= e2.cur_timestamp 
and ((e.vis = 'nlgintro' and (e.type = 2 OR e.type = 3) )OR (e.vis = 'graphintro' and (e.type = 0 OR e.type = 1) ))
and e2.vis = 'Combination';

--Graph intro
SELECT g.* FROM ourspaces3.log_graphVis g join ourspaces3.log_provviseval e on (g.userid = e.userid)
where 
g.cur_timestamp < e.cur_timestamp 
and vis = 'graphintro';

--Graphical from Graph Task
SELECT distinct g.* FROM ourspaces3.log_graphVis g join ourspaces3.log_provviseval e on (g.userid = e.userid)
join ourspaces3.log_provviseval e2 on (g.userid = e.userid)
where 
g.cur_timestamp >= e.cur_timestamp 
and g.cur_timestamp <= e2.cur_timestamp 
and e.vis = 'graphintro'
and e2.vis = 'Graph';


--Graphical from Combined
SELECT distinct g.* FROM ourspaces3.log_graphVis g join ourspaces3.log_provviseval e on (g.userid = e.userid)
join ourspaces3.log_provviseval e2 on (g.userid = e.userid)
where 
g.cur_timestamp >= e.cur_timestamp 
and g.cur_timestamp <= e2.cur_timestamp 
and ((e.vis = 'nlgintro' and (e.type = 2 OR e.type = 3) )OR (e.vis = 'graphintro' and (e.type = 0 OR e.type = 1) ))
and e2.vis = 'Combination';

--Train time and errors
SELECT eT.time,errors, err.vis FROM ourspaces3.log_provviseval e1
join ourspaces3.log_provviseval eT on (e1.userid = eT.userid)
join ourspaces3.eval_errors err on (e1.userid = err.userid and e1.vis = err.vis)
where
eT.dataset = 'examples'
and (
(eT.vis = 'nlgintro' AND e1.vis = 'NLG') 
OR
(eT.vis = 'graphintro' AND e1.vis = 'Graph') 
OR
(eT.vis = 'nlgintro' AND e1.vis = 'Combination' and (e1.type = 2 || e1.type = 3)) 
OR
(eT.vis = 'graphintro' AND e1.vis = 'Combination' and (e1.type = 0 || e1.type = 1)) 
);

--Usage of the two methods in combined method
SELECT g.* FROM ourspaces3.log_graphVis g 
join ourspaces3.log_provviseval e on (g.userid = e.userid)
join ourspaces3.log_provviseval e2 on (g.userid = e2.userid)
where 
g.cur_timestamp >= e.cur_timestamp 
and g.cur_timestamp <= e2.cur_timestamp 
AND ((e.vis = 'nlgintro' AND (e2.type = 2 || e2.type = 3)) 
OR
(e.vis = 'graphintro' AND (e2.type = 0 || e2.type = 1)) )
and e2.vis = 'Combination';

SELECT distinct nlg.* FROM ourspaces3.log_nlgRequest nlg 
join ourspaces3.log_provviseval e on (nlg.userid = e.userid)
join ourspaces3.log_provviseval e2 on (nlg.userid = e2.userid)
where 
nlg.cur_timestamp >= e.cur_timestamp 
and nlg.cur_timestamp <= e2.cur_timestamp 
AND ((e.vis = 'nlgintro' and (e.type = 2 || e.type = 3)) 
OR
(e.vis = 'graphintro' and (e.type = 0 || e.type = 1)) )
and e2.vis = 'Combination';


--Usage of the two methods based on the type - if the first method influenced the usage in the second task
SELECT g.* FROM ourspaces3.log_graphVis g 
join ourspaces3.log_provviseval e on (g.userid = e.userid)
join ourspaces3.log_provviseval e2 on (g.userid = e2.userid)
where 
g.cur_timestamp >= e.cur_timestamp 
and g.cur_timestamp <= e2.cur_timestamp 
and (e.type = 0 OR e.type = 1)
AND ((e.vis = 'nlgintro' AND (e2.type = 2 || e2.type = 3)) 
OR
(e.vis = 'graphintro' AND (e2.type = 0 || e2.type = 1)) )
and e2.vis = 'Combination';

SELECT distinct nlg.* FROM ourspaces3.log_nlgRequest nlg 
join ourspaces3.log_provviseval e on (nlg.userid = e.userid)
join ourspaces3.log_provviseval e2 on (nlg.userid = e2.userid)
where 
nlg.cur_timestamp >= e.cur_timestamp 
and nlg.cur_timestamp <= e2.cur_timestamp 
and (e.type = 2 OR e.type = 3)
AND ((e.vis = 'nlgintro' and (e.type = 2 || e.type = 3)) 
OR
(e.vis = 'graphintro' and (e.type = 0 || e.type = 1)) )
and e2.vis = 'Combination';


--Answers
SELECT count(*),vis, errors FROM ourspaces3.eval_errors err
group by vis, errors;