# Upute za kontribuciju

Za svaku promjenu napraviti novi branch proizvoljnog imena (bitno je da se promjene ne pushaju odmah na glavne brancheve).  

Postoje 2 vrste promjena:
- nova funkcionalnost (**feat**)
- popravak bugova (**bugfix**)

Za svaku vrstu promjena treba pushati branch s promjenama na GitHub i onda treba napraviti
pull request na branch *master*. Pull request treba imenovati na jedan od sljedećih načina,
ovisno dodaje li se nova funkcionalnost ili bugfix.

**Za backend promjene**  
- back: feat: Added new GET route /user/[:id]
- back: bugfix: Route /usr/[:id] no longer throws NullPointerException on illegal ID

**Za frontend promjene**  
- front: feat: Added admin page (UI only, no functionality)
- front: bugfix: Fetching route /user/[:id] now properly returns an object instead of string

**Za docs promjene**
- docs: Added functional requirements