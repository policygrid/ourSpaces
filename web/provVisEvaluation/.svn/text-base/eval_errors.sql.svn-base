create view eval_errors AS
SELECT *, 0 as errors FROM ourspaces3.log_provviseval e 
where dataset = '1' and
a1 ='o2' and
a2 ='o3' and
a3 = 'o2'
UNION
SELECT *, 1 as errors FROM ourspaces3.log_provviseval e 
where dataset = '1' and
(
(a1 ='o2' and
a2 ='o3' and
a3 <> 'o2') OR 
(a1 ='o2' and
a2 <>'o3' and
a3 = 'o2') OR 
(a1 <>'o2' and
a2 ='o3' and
a3  = 'o2') 
)
UNION
SELECT *, 2 as errors FROM ourspaces3.log_provviseval e 
where dataset = '1' and
(
(a1 ='o2' and
a2 <>'o3' and
a3 <> 'o2') OR 
(a1 <>'o2' and
a2 <>'o3' and
a3 = 'o2') OR 
(a1 <>'o2' and
a2 ='o3' and
a3  <> 'o2') 
)


UNION
SELECT *, 3 as errors FROM ourspaces3.log_provviseval e 
where dataset = '1' and
(
(a1 <>'o2' and
a2 <>'o3' and
a3 <> 'o2')
)

UNION 
SELECT *, 0 as errors FROM ourspaces3.log_provviseval e 
where dataset = '2' and
a1='o2' and
a2='o4' and
a3='o1'

UNION
SELECT *, 1 as errors FROM ourspaces3.log_provviseval e 
where dataset = '2' and
(
(a1 ='o2' and
a2 ='o4' and
a3 <> 'o1') OR 
(a1 ='o2' and
a2 <>'o4' and
a3 = 'o1') OR 
(a1 <>'o2' and
a2 ='o4' and
a3  = 'o1') 
)


UNION
SELECT *, 2 as errors FROM ourspaces3.log_provviseval e 
where dataset = '2' and
(
(a1 ='o2' and
a2 <>'o4' and
a3 <> 'o1') OR 
(a1 <>'o2' and
a2 <>'o4' and
a3 = 'o1') OR 
(a1 <>'o2' and
a2 ='o4' and
a3  <> 'o1') 
)
UNION
SELECT *, 3 as errors FROM ourspaces3.log_provviseval e 
where dataset = '2' and
(
(a1 <>'o2' and
a2 <>'o4' and
a3 <> 'o1')
)
