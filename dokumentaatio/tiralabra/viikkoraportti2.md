## viikkoraportti 2

Viikko kului pitkälti ympäristön asentamisessa: vanha javalabraprojektin ruumis piti saada toimimaan uudessa ympäristössä siten, että tekoäly on liitettävissä siihen helposti ja koodit pidettävä mielellään täysin erillään. Tätä arkkitehtuuria askarrellessa tuli tutustuttua moduulipohjaisiin projekteihin, missä saman projektin sisällä on usemapi toisistaan erillinen moduuli: tavoitteena on saada tekoäly täysin erilliseen moduuliin arvostelun ja tarkastelun (sekä jatkokehityksen) helpottamiseksi.

Olen tyytyväinen että aikani sekoiltuani sain erillisten moduulien yhteiselon toimimaan: mutuilen että se on on enemmän 'pro' kuin piilottaa wanha peli ja uusi äly samaan moduuliin. Olettaisin että tämmöisen rakenteen hallitsemisesta on jatkossa hyötyä.

Tekoäly pelaa peliä kuin muutkin pelaajat: kun on sen vuoro, se tekee kertoo pelivelholle mitä haluaa tehdä, jonka jälkeen vuoro siirtyy seuraavalle. Älyä ei ole "sidottu" mihinkään kehoon (pelaajaolioon), vaan sille annetaan kontrolloitava keho ja pelitilanne kun siltä pyydetään siirtoa. Tämä tekee testauksesta helpompaa, minkä lisäksi riippuvuudet ovat selkeämmät. Tällä arkkitehtuurilla riittää, että tehdään vaan yksi tekoäly-olio, vaikka botin kontrolloimia pelaajia olisi useampikin.

Tekoälyn tulee olla nopea kaikissa tilanteissa, mikä pelin satunnaisen luonteen vuoksi ei pitäisi olla vaikeaa, sillä syvien pelipuiden tms laskeminen ei välttämättä ole kovinkaan hyödyllistä, koska satunnaisuudella on pelin kulun kannalta iso merkitys.

Ensimmäinen askel on saada äly kutsuttavaan muotoon, eli että se ottaa oikeat tiedot ja antaa siirron vastauksena. Seuraavaksi siirron tulisi olla aina laillinen. Tämän jälkeen siirrosta voi koittaa tehdä edes hieman fiksumman, lopuksi hienosäätää jos aikaa jää. Ennen hienosäätöä kaikki käytetyt tietorakenteet ja kirjastojen algoritmit pitää sitten kirjoittaa itse.

Tämä viikko on mennyt pitkälti töissä, ensi viikolla pitäisi olla hieman helpompaa. Tässä alkaakin olla korkea aika investoida tunteja tähän projektiin rankemmalla kädellä, ettei loppuprojektista tule 24/7 homma.

2.8. 3h *ympäristön asennus, .jar riippuvuuksien ja tiedostonlukuongelmien ihmettelyä*
4.8. 3h *ympäristö, build ja run toimimaan .jar -tiedostolla; älyn toiminta-ajatus*
4.8. 2h *pelin refaktorointia älyn yhteensopivuutta ajatellen, älyn toimintalogiikan hahmottelu*
