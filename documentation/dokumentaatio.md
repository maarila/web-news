##Uutissivusto

####Web-palvelinohjelmointi Java, syksy 2017  
__Harjoitustyö__

__Kuvaus:__

"Uutissivusto" on web-sovellus, joka mahdollistaa uutisten lisäämisen ja selaamisen web-selaimella. Uutissivuston etusivu tarjoaa käyttäjälle viisi uusinta uutista julkaisuajan mukaan lajiteltuna uusimmasta vanhimpaan. Uutisista on nähtävillä otsikko, ingressi ja kuva. Sivusto tarjoaa mahdollisuuden selata uutisia myös kattavammin niiden julkaisuajan perusteella. Tällöin näkyviin tulevat vain linkkeinä toimivat uutisen otsikko ja julkaisuaika. Sivusto pitää myös kirjaa luetuimmista uutisista, ja uutisia voikin selata suosituimmuuden pohjalta. Tällöin uutisten esillepano toimii kuten uutisten julkaisuajan perusteella selatessa. Yksittäisten uutisten sivuilla näkyvät myös viiden uusimman sekä viiden luetuimman uutisen listaukset.

Ylläpitäjälle uutissivusto tarjoaa kirjautumisen kautta pääsyn hallintapaneeliin, jossa järjestelmään voi lisätä uusia ja poistaa vanhoja uutisia. Jotta uutisen syöttö onnistuisi, uutiselle vaaditaan otsikko, ingressi, leipäteksti, julkaisuaika, kirjoittaja tai kirjoittajat, kategoria tai kategoriat sekä uutiskuva. Kuva tulee näkyviin niin palvelun etusivulle kuin yksittäisten uutisten sivuillekin. 

Uutisten kategoriat voi merkitä hallintasivuilla navigaatiopalkkiin lisättäviksi. Tämän jälkeen kyseisen kategorian kaikki uutiset ovat haettavissa pudotusvalikosta.

__Käyttötapaukset:__

As a user, I can get an overview of the latest news on the front page.

As a user, I can see all the latest news ordered by date.

As a user, I can see the most popular news ordered by times read.

As a user, I can browse news through various categories.

As an administrator, I can login and add new news stories or remove old ones.

__Tietokannan skeemat:__

	CREATE MEMORY TABLE PUBLIC.KIRJOITTAJA(
    ID BIGINT NOT NULL,
    NIMI VARCHAR(255)
    )

	CREATE MEMORY TABLE PUBLIC.UUTINEN_KATEGORIAT(
    UUTISET_ID BIGINT NOT NULL,
    KATEGORIAT_ID BIGINT NOT NULL
    )

	CREATE MEMORY TABLE PUBLIC.KUVA(
    ID BIGINT NOT NULL,
    KUVA BLOB
    )	

	CREATE MEMORY TABLE PUBLIC.UUTINEN_KIRJOITTAJAT(
    UUTISET_ID BIGINT NOT NULL,
    KIRJOITTAJAT_ID BIGINT NOT NULL
    )

	CREATE MEMORY TABLE PUBLIC.KATEGORIA(
    ID BIGINT NOT NULL,
    NIMI VARCHAR(255),
    VALIKOSSA BOOLEAN NOT NULL
    )

	CREATE MEMORY TABLE PUBLIC.UUTINEN(
    ID BIGINT NOT NULL,
    INGRESSI CLOB,
    JULKAISUAIKA TIMESTAMP,
    LEIPATEKSTI CLOB,
    LUETTU INTEGER,
    OTSIKKO VARCHAR(255),
    KUVA_ID BIGINT
    )

__Käyttöohje:__

Uutissivustoa voi selata sivun yläosan navigaatiopalkista. Uutisten syöttö tapahtuu KIRJAUDU-valinnan kautta. Kirjautumiseen tarvittavat tunnukset ovat:

Käyttäjätunnus: _admin_

Salasana: _password_

Sovellus vaatii, että jokaiseen kenttään syötetään tietoa. Kirjoittajat ja kategoriat on syötettävä pilkuilla eroteltuina. Mikäli kategoria-kohdassa lisää rastin ruutuun "Lisää kategoriat navigaatiopalkkiin", sovellus lisää uutissivuilla näkyvään kategoria-pudotusvalikkoon tekstikentässä olleet kategoriat, mikäli ne eivät jo ole siellä.


__Puuttuvat ominaisuudet ja muut puutteet:__

Autentikointia ja validointia ei ole toteutettu kurssin viikolla 6 esitellyllä tavalla aikataulusyistä, vaan ylläpitäjän kirjautumisen tunnistaminen hoidetaan HttpSessionilla. Syötteiden validointi rajoittuu siihen, että kaikki kentät ovat hallintapaneelissa pakollisia.

Luetuimpia uutisia seurataan aikarajattomasti, eli lukukerrat kirjataan kokonaislukuina sen sijaan, että käytettäisiin esimerkiksi LocalDate(Time)a. 

Uutisia voi poistaa, vaan ei muokata.

Pieninä puutteina sovellus ei reagoi väärillä tunnuksilla kirjautumisyritykseen muuten kuin tyhjentämällä tekstikentät. Sama pätee uutisten syöttöön — onnistuneen syötön merkiksi kentät tyhjenevät. Kategorioiden selaaminen on toteutettu rujonpuoleisella select-pudotusvalikolla, joka on irrallaan muusta navigaatiosta. Sovellus on muutenkin ulkoasultaan varsin pelkistetty. Testaaminen voisi myös olla laajempaa.