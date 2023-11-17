# Upute za kontribuciju

Za svaku promjenu napraviti novi branch proizvoljnog imena (bitno je da se promjene ne pushaju odmah na glavne brancheve).  

Postoje 3 vrste promjena:
- nova funkcionalnost (**feat**)
- popravak bugova (**bugfix**)
- generalna reorganizacija koda (**refractor**)

Za svaku vrstu promjena treba pushati branch s promjenama na GitHub i onda treba napraviti
pull request na branch `master`. Pull request treba imenovati na jedan od sljedećih načina,
ovisno dodaje li se nova funkcionalnost, bugfix ili refractor:

**Za backend promjene**  
- back: feat: Added new GET route /user/[:id]
- back: bugfix: Route /user/[:id] no longer throws NullPointerException on illegal ID
- back: refractor: Optimized calls for database when triggering /user/[:id] and /post/[:id] routes

**Za frontend promjene**  
- front: feat: Added admin page (UI only, no functionality)
- front: bugfix: Fetching route /user/[:id] now properly returns an object instead of string
- front: refractor: Routes now use component wrappers for rendering header and footer

**Za promjene dokumentacije**  
- docs: Added functional requirements