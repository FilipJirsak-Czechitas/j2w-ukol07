# Úkol 7 – Blog

Vytvoříme jednoduchou aplikaci pro zobrazování blogových zápisků. Na titulní stránce se bude zobrazovat přehled dvaceti posledních zápisků, nejnovější bude na
prvním místě. Zobrazovat se bude titulek, perex, datum publikování a autor. U zápisku bude odkaz např. „Přečíst“, který povede na stránku s detailem, kde bude
vedle výše uvedených informací také celý zápisek. URL zápisku bude ve tvaru `/post/{slug}`, kde `slug` je hodnota z odpovídajícího sloupce v databázi. Na
stránce s detailem bude odkaz zpět na titulní stránku.

Databáze obsahuje jednu tabulku pojmenovanou `post`. Jak se tabulka vytváří se podívej v souboru `src/resources/db/migration/V1__init.sql`.

* `id` – číselný identifikátor zápisku, primární klíč – v Javě pro něj použij typ `Long`; aplikaci v tuto chvíli k ničemu nebude, ale v databázi má každý slušně
  vychovnaý záznam svůj jednoznačný identifikátor
* `slug` – část URL za `/post/`, která identifikuje zápisek (tzv. „hezká URL“)
* `author` – jméno autora
* `title` – titulek zápisku
* `perex` – perex, HTML kód prvního odstavce zápisku, který se zobrazuje na úvodní stránce
* `body` – pokračování zápisku za perexem, HTML kód
* `published` – datum publikování, pokud bude `NULL` nebo v budoucnosti, zápisek se ještě na titulní stránce nezobrazuje (vyzkoušej si, jak se chová databáze,
  když je v záznamu hodnota `NULL` a v podmínce je řečeno, že datum musí být menší než nějaká hodnota)

Tabulka s daty je po prvním spuštění aplikace prázdná, pro otestování bude potřeba si nějaké zápisky do tabulky vložit pomocí nástrojů v IntelliJ Idea.
Připojovací URL, které se zadává při konfiguraci panelu Database v IntelliJ Idea, najdeš v souboru `src/main/resources/application.yaml`.

1. Použij toto repository jako šablonu (Use this template), ze které si vytvoříš repository ve svém účtu na GitHubu.
1. Naklonuj si repository **ze svého účtu** na GitHubu na lokální počítač.
1. Spusť si naklonovanou aplikaci, aby se vytvotřila databáze. V prohlížeči se na stránce [http://localhost:8080/](http://localhost:8080/) zatím bude zobrazovat
   jen chyba, v aplikaci není žádný controller.
1. Zprovozni si panel Database v IntelliJ Idea, ať si můžeš ověřit, co je v databázi (je tam jen prázdná tabulka, žádné zápisky 😉). Připojovací URL, které se
   zadává při konfiguraci panelu *Database* v IntelliJ Idea, najdeš v souboru `src/main/resources/application.yaml`. Nejjednodušší je použít *DataSource from
   URL*.
1. Po spuštění aplikace je databáze prázdná, zakládací skript neplní žádná data. Použij v IntelliJ Idea panel *Database* pro připojení k databázi a přidej
   několik příspěvků do budoucího blogu. V perexu a tělu (`body`) můžeš použít HTML kód – určitě použij alespoň odstavce `<p>…</p>`.
1. Vytvoř entitu `Post` a v ní vytvoř fieldy a properties odpovídající databázové tabulce. Nezpomeň na anotace `@Entity`, `@Id` a `@GeneratedValue`.
1. Vytvoř `JpaRepository` pro entitu `Post` (pojmenuj ji `PostRepository`).
1. Vytvoř službu `PostService`, která bude pomoc `@Autowired` získávat `PostRepository`.
1. Ve službě `PostService` vytvoř metodu `list()`, která bude vracet seznam všech postů (zatím bez stránkování a řazení). Dále tam vytvoř metodu
   `singlePost(String slug)`, která najde jeden post podle zadaného `slug` a ten vrátí.
1. Vytvoř controller a v něm dvě metody, pro zobrazení úvodí stránky se seznamem postů a pro zobrazení jednoho kompletního postu. Controller bude používat
   službu
   `PostService`, kterou získá pomocí `@Autowired`. Nemusíš řešit případ, když si uživatel vymyslí URL postu, který neexistuje.
1. Vytvoř šablony pro obě metody controlleru. Na vzhledu nezáleží :-) Pro vložení HTML kódu z modelu do šablony je nutné místo použít ve Freemarkeru zápis
   `${value?no_esc}`, který zajistí, že Freemarker nebude převádět znaky `<` a `>`, ale vloží je bezezměny do výsledného souboru. Detaily najdeš v dokumentaci
   [no_esc](https://freemarker.apache.org/docs/ref_builtins_string.html#ref_builtin_no_esc) Freemarkeru.
1. Uprav metodu `list()` v `PostService` tak, aby používala `Pageable` a omezila výsledek na 20 záznamů. Pro vytvoření správného `Pageable` použij statickou
   metodu `PageRequest.of(0, 20)`. Vytvoř v repository metodu, která bude vracet Page<Post>, bude používat `Pageable` pro omezení počtu záznamů, načte pouze
   posty,
   které mají datum publikace a není v budoucnosti, a seřadí záznamy sestupně podle data publikace. Pro řazení se nebude používat položka `sort` z `Pageable` (
   to se používá v případě, kdy má uživoatel mít možnost měnit způsob řazení – my ale chceme zápisky seřadit vždy od nejnovějšího po nejstarší). Místo toho se
   použije správný název metody v repository (součástí názvu metody bude tedy text `OrderBy`).
1. *Bonus*: Můžeš upravit šablonu pro výpis seznamu zápisků tak, aby bylo možné stránkami listovat. Nepoužije se ale číslování stránek, místo toho budou dole
   na stránce jen odkazy „předchozí“ a „další“. Použij k tomu metody `hasPrevious()` a `hasNext()` na rozhraní `Page`.
1. Zkontroluj, zda vše funguje.
1. *Commitni* a *pushnni* změny (výsledný kód) do svého repository na GitHubu.
1. Vlož odkaz na své repository do úkolu na portálu https://moje.czechitas.cz.
1. *Super bonus*: Můžeš do aplikace přidat i administraci – stránku, přes kterou bude možné přidávat zápisky, upravovat je a mazat.

## Odkazy

* odkaz na stránku [Lekce 10](https://java.czechitas.cz/2024-jaro/java-2-online/lekce-10.html)
* Java SE 21 [Javadoc](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/) – dokumentace všech tříd, které jsou součástí základní Javy ve verzi 21.
* Dokumentace [Spring Boot](https://spring.io/projects/spring-boot#learn) – odsud je anotace `@SpringBootApplication` a třída `SpringApplication`.
* Dokumentace [Spring Framework](https://spring.io/projects/spring-framework#learn) – odsud jsou anotace `@Controller`, `@GetRequest` a třída `ModelAndView`.
* Dokumentace [Freemarker](https://freemarker.apache.org/docs/) – šablonovací systém pro HTML použitý v projektu.
* [Unsplash](https://unsplash.com) – obrázky a fotografie k použití zdarma
