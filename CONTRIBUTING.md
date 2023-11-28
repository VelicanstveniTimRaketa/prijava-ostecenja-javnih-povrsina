# Upute za kontribuciju

Za svaku promjenu napraviti novi branch proizvoljnog imena (bitno je da se promjene ne pushaju odmah na glavne brancheve).  

Postoje 3 vrste promjena:
- nova funkcionalnost (**feat**)
- popravak bugova (**fix**)
- generalna reorganizacija koda (**refractor**)

Svaki commit treba imati commit message postavljen na jedan od sljedećih načina, ovisno dodaje li se
nova funkcionalnost, bugfix ili refractor:

**Za backend promjene**  
- back: feat: Added new GET route /user/[:id]
- back: fix: Route /user/[:id] no longer throws NullPointerException on illegal ID
- back: refractor: Optimized calls for database when triggering /user/[:id] and /post/[:id] routes

**Za frontend promjene**  
- front: feat: Added admin page (UI only, no functionality)
- front: fix: Fetching route /user/[:id] now properly returns an object instead of string
- front: refractor: Routes now use component wrappers for rendering header and footer

**Za promjene dokumentacije**  
- docs: Added functional requirements

Nakon određenog broja commita, potrebno je pushati branch na GitHub i napraviti pull request na branch `develop`.
Pull request message je potrebno imenovati na jednak način kao i commite, s tim da se vrsta promjene
(`feat`, `fix` ili `refractor`) može izostaviti ako nijedna nije prikladna.

Merge requestove razrješavaju maintaineri projekta. Commiti koji su nastali merganjem se označavaju `merge:` prefixom, na primjer:
- merge: back: Add routes for fetching user data
