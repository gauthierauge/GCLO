--
-- PostgreSQL database dump
--

-- Dumped from database version 15.12 (Debian 15.12-1.pgdg120+1)
-- Dumped by pg_dump version 16.4

-- Started on 2025-03-20 09:36:15

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 941 (class 1247 OID 25417)
-- Name: montant_tva_escompte_par_commande; Type: TYPE; Schema: public; Owner: JpA
--

CREATE TYPE public.montant_tva_escompte_par_commande AS (
	montanttvaescompte numeric(10,5)
);


ALTER TYPE public.montant_tva_escompte_par_commande OWNER TO "JpA";

--
-- TOC entry 944 (class 1247 OID 25420)
-- Name: montant_tva_escompte_par_facture; Type: TYPE; Schema: public; Owner: JpA
--

CREATE TYPE public.montant_tva_escompte_par_facture AS (
	montanttvaescompte numeric(10,5)
);


ALTER TYPE public.montant_tva_escompte_par_facture OWNER TO "JpA";

--
-- TOC entry 947 (class 1247 OID 25423)
-- Name: montant_tva_par_facture; Type: TYPE; Schema: public; Owner: JpA
--

CREATE TYPE public.montant_tva_par_facture AS (
	montanttvafacture numeric(10,5)
);


ALTER TYPE public.montant_tva_par_facture OWNER TO "JpA";

--
-- TOC entry 284 (class 1255 OID 25424)
-- Name: bondelivraisonclienttransformationenfacture(character varying); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.bondelivraisonclienttransformationenfacture(_codelivraison character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$ 

DECLARE
  _strcodefacture character varying (50);
  _nbadressefacturation integer;
  _codetiers character varying (50);
  _raisonsocialetiersautreadresse character varying(50);
  _adresse1tiersautreadresse character varying(100);
  _adresse2tiersautreadresse character varying(100);
  _codepostaltiersautreadresse character varying(50);
  _villetiersautreadresse character varying(50);
  _paystiersautreadresse character varying(50);
  
BEGIN
    _strcodefacture := (SELECT generecodefacturationclient());

    SELECT codetiers INTO _codetiers FROM Livraison__tbl WHERE codelivraison = _codelivraison;

    UPDATE Livraison__tbl SET codeetat = 3 WHERE codelivraison = _codelivraison;	-- FACTURER

    INSERT INTO Facture__tbl ( codefacture, typefacture, codetiers, libellefacture, saisonfacture, codelivraison, codeetat, 
				 datefacture, raisonsocialetiers, adresse1tiers, adresse2tiers, codepostaltiers, villetiers, paystiers,
				 telephone1tiers, telephone2tiers, telephone3tiers, telecopietiers, adressedemessagerietiers, numerosirettiers, tauxtaxeparafiscale, tauxtvataxeparafiscale,
				fraisdeportht, tauxtvafraisdeport, languedecorrespondance, devisefacture, exonerationtva, exonerationtpf, avoirsurarticle) 
    SELECT _strcodefacture, 'FACTURE', codetiers, 'FACTURE POUR LE BON DE LIVRAISON N° ' || _codelivraison, saisonlivraison, _codelivraison, 1, now(), 
           raisonsocialetiers, adresse1tiers, adresse2tiers, codepostaltiers, villetiers, paystiers,
           telephone1tiers, telephone2tiers, telephone3tiers, telecopietiers, adressedemessagerietiers, numerosirettiers, ( SELECT tauxtaxeparafiscale FROM Parametre__tbl WHERE compteurparametre = 1 ), 
          ( SELECT tauxtvadefaut FROM Parametre__tbl WHERE compteurparametre = 1 ),
	  fraisdeportht, tauxtvafraisdeport, languedecorrespondance, deviselivraison, exonerationtva, exonerationtpf, true
    FROM Livraison__tbl
    WHERE codelivraison = _codelivraison;


    SELECT COUNT(*) INTO _nbadressefacturation FROM TiersAutreAdresse__tbl WHERE codetiers = _codetiers AND typeadressetiersautreadresse = 'FACTURATION' LIMIT 1;


    IF ( _nbadressefacturation = 1 ) THEN

	SELECT raisonsocialetiersautreadresse, adresse1tiersautreadresse, adresse2tiersautreadresse, codepostaltiersautreadresse, villetiersautreadresse, paystiersautreadresse 
	INTO _raisonsocialetiersautreadresse, _adresse1tiersautreadresse, _adresse2tiersautreadresse, _codepostaltiersautreadresse, _villetiersautreadresse, _paystiersautreadresse
	FROM TiersAutreAdresse__tbl WHERE codetiers = _codetiers AND typeadressetiersautreadresse = 'FACTURATION' LIMIT 1;

        UPDATE Facture__tbl
	SET raisonsocialetiers = _raisonsocialetiersautreadresse,
	    adresse1tiers      = _adresse1tiersautreadresse,
	    adresse2tiers      = _adresse2tiersautreadresse,
	    codepostaltiers    = _codepostaltiersautreadresse,
	    villetiers         = villetiersautreadresse, 
	    paystiers          = paystiersautreadresse
	WHERE codefacture = _strcodefacture;

    END IF;

   INSERT INTO FactureLigne__tbl ( codefacture, typefacture, codelivraison, compteurlivraisonligne, codearticle, modelearticle, designationarticle, colorisarticle, 
                                      nbarticletailleunique, nbarticletailles, nbarticletaillem, nbarticletaillel, nbarticletaillexl, nbarticletaillexxl, nbarticle,
                                      prixdeventehtarticle, tauxtvaarticle, prixdeventettcarticle ) 
    SELECT _strcodefacture, 'FACTURE', codelivraison, compteurlivraisonligne, codearticle, modelearticle, designationarticle, colorisarticle, 
           nbarticletailleunique, nbarticletailles, nbarticletaillem, nbarticletaillel, nbarticletaillexl, nbarticletaillexxl, nbarticle, 
           prixdeventehtarticle, tauxtvaarticle, prixdeventettcarticle 
    FROM LivraisonLigne__tbl 
    WHERE codelivraison = _codelivraison
    AND ( ( COALESCE(nbarticletailleunique,0 )  ) <> 0 OR ( COALESCE(nbarticletailles,0 )  ) <> 0 OR ( COALESCE(nbarticletaillem, 0)  ) <> 0 
						  OR ( COALESCE(nbarticletaillel, 0)  ) <> 0  
						  OR ( COALESCE(nbarticletaillexl, 0) ) <> 0 
						  OR ( COALESCE(nbarticletaillexxl, 0) ) <> 0 )
    ORDER BY compteurlivraisonligne;
 
RETURN _strcodefacture;
END;
$$;


ALTER FUNCTION public.bondelivraisonclienttransformationenfacture(_codelivraison character varying) OWNER TO "JpA";

--
-- TOC entry 301 (class 1255 OID 25425)
-- Name: commandeclienttransformationenbondelivraison(character varying); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.commandeclienttransformationenbondelivraison(_codecommande character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$ 

DECLARE
  _strcodelivraison character varying (50);
  _nbadresselivraison integer;
  _codetiers character varying (50);
  _raisonsocialetiersautreadresse character varying(50);
  _adresse1tiersautreadresse character varying(100);
  _adresse2tiersautreadresse character varying(100);
  _codepostaltiersautreadresse character varying(50);
  _villetiersautreadresse character varying(50);
  _paystiersautreadresse character varying(50);
  
BEGIN
    _strcodelivraison := (SELECT genereCodeLivraisonClient());

    SELECT codetiers INTO _codetiers FROM Commande__tbl WHERE codecommande = _codecommande;

    UPDATE Commande__tbl SET codeetat = 4 WHERE codecommande = _codecommande;	-- EN COURS DE LIVRAISON

    INSERT INTO Livraison__tbl ( codelivraison, codetiers, libellelivraison, saisonlivraison, codecommande, codeetat, 
				 datelivraison, raisonsocialetiers, adresse1tiers, adresse2tiers, codepostaltiers, villetiers, paystiers,
				 telephone1tiers, telephone2tiers, telephone3tiers, telecopietiers, adressedemessagerietiers, numerosirettiers,
				fraisdeportht, tauxtvafraisdeport, exonerationtva, exonerationtpf, tauxtaxeparafiscale, tauxtvataxeparafiscale, languedecorrespondance, deviselivraison ) 
    SELECT _strcodelivraison, codetiers, 'BON DE LIVRAISON POUR LA COMMANDE N° ' || _codecommande, saisoncommande, codeCommande, 1, now(), 
           raisonsocialetiers, adresse1tiers, adresse2tiers, codepostaltiers, villetiers, paystiers,
           telephone1tiers, telephone2tiers, telephone3tiers, telecopietiers, adressedemessagerietiers, numerosirettiers,
	   fraisdeportht, tauxtvafraisdeport, exonerationtva, exonerationtpf, tauxtaxeparafiscale, tauxtvataxeparafiscale, languedecorrespondance, devisecommande
    FROM Commande__tbl
    WHERE codecommande = _codecommande;

    SELECT COUNT(*) INTO _nbadresselivraison FROM TiersAutreAdresse__tbl WHERE codetiers = _codetiers AND typeadressetiersautreadresse = 'LIVRAISON' LIMIT 1;


    IF ( _nbadresselivraison = 1 ) THEN

	SELECT raisonsocialetiersautreadresse, adresse1tiersautreadresse, adresse2tiersautreadresse, codepostaltiersautreadresse, villetiersautreadresse, paystiersautreadresse 
	INTO _raisonsocialetiersautreadresse, _adresse1tiersautreadresse, _adresse2tiersautreadresse, _codepostaltiersautreadresse, _villetiersautreadresse, _paystiersautreadresse
	FROM TiersAutreAdresse__tbl WHERE codetiers = _codetiers AND typeadressetiersautreadresse = 'LIVRAISON' LIMIT 1;

        UPDATE Livraison__tbl
	SET raisonsocialetiers = _raisonsocialetiersautreadresse,
	    adresse1tiers      = _adresse1tiersautreadresse,
	    adresse2tiers      = _adresse2tiersautreadresse,
	    codepostaltiers    = _codepostaltiersautreadresse,
	    villetiers         = villetiersautreadresse, 
	    paystiers          = paystiersautreadresse
	WHERE codeLivraison = _strcodelivraison;

    END IF;


    INSERT INTO LivraisonLigne__tbl ( codelivraison, codecommande, compteurcommandeligne, codearticle, modelearticle, designationarticle, colorisarticle, 
                                      nbarticletailleunique, nbarticletailles, nbarticletaillem, nbarticletaillel, nbarticletaillexl, nbarticletaillexxl, 
                                      nbarticletailleuniqueencommande, nbarticletaillesencommande, nbarticletaillemencommande, nbarticletaillelencommande, nbarticletaillexlencommande, nbarticletaillexxlencommande, 
                                      nbarticletailleuniquesoldelivraison, nbarticletaillessoldelivraison, nbarticletaillemsoldelivraison, nbarticletaillelsoldelivraison, nbarticletaillexlsoldelivraison, nbarticletaillexxlsoldelivraison, 
				      nbarticleencommande,
				      nbarticlesoldelivraison,	
                                      prixdeventehtarticle, tauxtvaarticle, prixdeventettcarticle ) 
    SELECT _strcodelivraison, codecommande, compteurcommandeligne, codearticle, modelearticle, designationarticle, colorisarticle, 
           0, 0, 0, 0, 0, 0, 
           nbarticletailleunique, nbarticletailles, nbarticletaillem, nbarticletaillel, nbarticletaillexl, nbarticletaillexxl, 
           ( COALESCE(nbarticletailleunique, 0) - COALESCE(nbarticletailleuniquelivre, 0) ), ( COALESCE(nbarticletailles, 0) - COALESCE(nbarticletailleslivre, 0) ), 
	   ( COALESCE(nbarticletaillem, 0) - COALESCE(nbarticletaillemlivre, 0) ), 
           ( COALESCE(nbarticletaillel, 0) - COALESCE(nbarticletaillellivre, 0) ), ( COALESCE(nbarticletaillexl, 0) - COALESCE(nbarticletaillexllivre, 0) ), 
	   ( COALESCE(nbarticletaillexxl, 0) - COALESCE(nbarticletaillexxllivre, 0) ), 
           nbarticle, 
	   ( COALESCE(nbarticle, 0) - COALESCE(nbarticlelivre, 0 ) ),
           prixdeventehtarticle, CASE WHEN tauxtvaarticle=19.6 THEN 20.0 ELSE tauxtvaarticle END, prixdeventettcarticle 
    FROM CommandeLigne__tbl 
    WHERE codecommande = _codecommande
    AND ( ( COALESCE(nbarticletailleunique, 0) - COALESCE(nbarticletailleuniquelivre, 0) ) > 0  OR ( COALESCE(nbarticletailles, 0) - COALESCE(nbarticletailleslivre, 0) ) > 0 
												OR ( COALESCE(nbarticletaillem, 0) - COALESCE(nbarticletaillemlivre, 0) )  > 0  
												OR ( COALESCE(nbarticletaillel, 0) - COALESCE(nbarticletaillellivre, 0) )  > 0  
												OR ( COALESCE(nbarticletaillexl, 0) - COALESCE(nbarticletaillexllivre, 0) ) > 0 
												OR ( COALESCE(nbarticletaillexxl, 0) - COALESCE(nbarticletaillexxllivre, 0) ) > 0 )
    ORDER BY compteurcommandeligne;

RETURN _strcodelivraison;
END;
$$;


ALTER FUNCTION public.commandeclienttransformationenbondelivraison(_codecommande character varying) OWNER TO "JpA";

--
-- TOC entry 289 (class 1255 OID 42455)
-- Name: exportcomptablefacture(date, date); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.exportcomptablefacture(_datedebut date, _datefin date) RETURNS TABLE(_date_facture date, _codejournal character varying, _compte character varying, _debit numeric, _credit numeric, _libelle character varying, _numeropiece character varying)
    LANGUAGE plpgsql
    AS $$

DECLARE
	mviews RECORD;
 
	_typefacture 	character varying(25); 
	_codejournal 	character varying(2);
	_date_facture 	date;
	_compte 	character varying(8);
	_libelle 	character varying(25);
	_debit 		numeric;
	_credit 	numeric; 
	_numeropiece 	character varying(7);
	_intitulecompte character varying(25);
	_typevente	integer;		-- 1 FRANCE; 2 EU; 3 HORS EU
	_taux_usd_eur	numeric;
	_devisefacture 	character varying(5);

BEGIN
	DROP TABLE IF EXISTS tmpExportFacture;
	CREATE TEMPORARY TABLE tmpExportFacture (codejournal 	character varying(2), 
						 date_facture 	date, 
						 compte 	character varying(8),  
						 libelle 	character varying(25), 
						 debit 		numeric DEFAULT 0.0, 
						 credit 	numeric DEFAULT 0.0, 
						 numeropiece 	character varying(7), 
						 intitulecompte character varying(25) ) 
	ON commit DROP ;

	DROP TABLE IF EXISTS tmpNewExportFacture;
	CREATE TEMPORARY TABLE tmpNewExportFacture (date_facture 	date,	
							codejournal 	character varying(2), 							 
							compte 		character varying(8),  
							debit 		numeric DEFAULT 0.0, 
							credit 		numeric DEFAULT 0.0, 
							libelle 	character varying(25), 
							numeropiece 	character varying(7)) 
	ON commit DROP ;

     FOR mviews IN SELECT datefacture, codefacture, typefacture, codetiers, ROUND(netcommercialht, 2) AS netcommercialht, ROUND(montantbruthtfacture, 2) AS montantbruthtfacture, 
							ROUND(montanttotalttcfacture, 2) AS montanttotalttcfacture, ROUND(montanttotaltvafacture, 2) AS montanttotaltvafacture, 
							ROUND(montanttaxeparafiscale, 2) AS montanttaxeparafiscale, 
							ROUND(fraisdeportht, 2) AS fraisdeportht, ROUND(montantescompte, 2) AS montantescompte, ROUND(montantremise, 2) AS montantremise, ROUND(montantacompteverse, 2) AS montantacompteverse,
							ROUND(montantacompteverseht, 2) AS montantacompteverseht, ROUND(montanttvaacompteverse, 2) AS montanttvaacompteverse, devisefacture
		   FROM facture__tbl 
		   WHERE datefacture BETWEEN _datedebut AND _datefin
		   AND typefacture <> 'FACTURE ACOMPTE'
		   ORDER BY codefacture
		   LOOP
		   
	_codejournal := 'VE';
	
	--SELECT CASE WHEN pays = 'FRANCE' THEN 1
	--	    WHEN COALESCE(unioneuropeenne, false) THEN 2
	--	    WHEN COALESCE(horsunioneuropeenne, false) THEN 3
	--	END
	--INTO _typevente
	--FROM pays__tbl
	--WHERE pays = (SELECT paystiers FROM tiers__tbl WHERE codetiers = mviews.codetiers);
	
	SELECT categorietiers INTO _typevente FROM tiers__tbl WHERE codetiers = mviews.codetiers;
	
	SELECT comptetiers INTO _compte  FROM tiers__tbl WHERE codetiers = mviews.codetiers; 
	
	SELECT SUBSTR(raisonsocialetiers,0,25) INTO _libelle FROM tiers__tbl WHERE codetiers = mviews.codetiers; 

	SELECT COALESCE(1/ratetauxchangeusd, 1) INTO _taux_usd_eur FROM tauxchangeusd__tbl WHERE datetauxchangeusd = mviews.datefacture;

	IF _taux_usd_eur = NULL THEN
		_taux_usd_eur := 1.0;
	END IF;

	IF mviews.devisefacture != 'USD' THEN
		_taux_usd_eur := 1.0;
	END IF;
	
	
	IF mviews.typefacture = 'FACTURE' THEN
		_debit  := mviews.montanttotalttcfacture * _taux_usd_eur;
		_credit := 0.0;
	END IF;
	
	IF mviews.typefacture = 'AVOIR' THEN
		_debit  := 0.0;
		_credit := mviews.montanttotalttcfacture * -1 * _taux_usd_eur;
	END IF;
	
	_intitulecompte := _libelle;

 	-- INSERTION DE LA LIGNE DE VENTE									 
	INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);

	
	-- INSERTION DE LA LIGNE ACOMPTE SI ACOMPTE <> 0 le montant HT
	IF (mviews.montantacompteverse <> 0 AND mviews.typefacture = 'FACTURE' ) THEN
		IF _typevente = 1 THEN 
			_compte := '41910000';
		END IF;
		IF _typevente = 2 THEN 
			_compte := '41912000';
		END IF;
		IF _typevente = 3 THEN 
			_compte := '41918000 ';
		END IF;

		_debit  := mviews.montantacompteverseht * _taux_usd_eur;
		_credit := 0.0;
		
		INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);
	END IF;

	-- INSERTION DE LA LIGNE MONTANT HT
	IF _typevente = 1 THEN 
		_compte := '70100000';
	END IF;
	IF _typevente = 2 THEN 
		_compte := '70102000';
	END IF;
	IF _typevente = 3 THEN 
		_compte := '70118000';
	END IF;
	     
	IF mviews.typefacture = 'FACTURE' THEN
		_debit  := 0.0;
		--_credit := mviews.netcommercialht;
		_credit := mviews.montantbruthtfacture * _taux_usd_eur;
	END IF;
	IF mviews.typefacture = 'AVOIR' THEN
		--_debit  := mviews.netcommercialht * -1;
		_debit  := mviews.montantbruthtfacture * -1 * _taux_usd_eur;
		_credit := 0.0;
	END IF;
	
	_intitulecompte := 'VENTES MARCHANDISES';
	
	INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);

	-- INSERTION DE LA LIGNE PORT HT
	IF _typevente = 1 THEN 
		_compte := '70850000';
	END IF;
	IF _typevente = 2 THEN 
		_compte := '70802200';
	END IF;
	IF _typevente = 3 THEN 
		_compte := '70808900';
	END IF;
	     
	IF mviews.typefacture = 'FACTURE' THEN
		_debit  := 0.0;
		_credit := mviews.fraisdeportht * _taux_usd_eur;
	END IF;
	IF mviews.typefacture = 'AVOIR' THEN
		_debit  := mviews.fraisdeportht * -1 * _taux_usd_eur;
		_credit := 0.0;
	END IF;
	
	_intitulecompte := 'PORTS/VENTES';
	
	INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);

	-- INSERTION DE LA LIGNE TVA	
	IF _typevente = 1 THEN 
		_compte := '44571100';
	END IF;
	IF _typevente = 2 THEN 
		_compte := '44571100';
	END IF;
	IF _typevente = 3 THEN 
		_compte := '44571100';
	END IF;

	IF mviews.typefacture = 'FACTURE' THEN
		_debit  := 0.0;
		_credit := mviews.montanttotaltvafacture * _taux_usd_eur - mviews.montanttvaacompteverse * _taux_usd_eur ;
	END IF;
	IF mviews.typefacture = 'AVOIR' THEN
		_debit  := mviews.montanttotaltvafacture * -1 * _taux_usd_eur - mviews.montanttvaacompteverse * -1 * _taux_usd_eur;
		_credit := 0.0;
	END IF;
	
	_intitulecompte := 'TVA COLLECTEE';
	
	INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);

	-- INSERTION DE LA LIGNE TAXE PARAFISCALE
	IF (_typevente = 1) OR (_typevente = 3) THEN 
		_compte := '70853000';
	END IF;
	IF (_typevente = 2) THEN 
		_compte := '70852200';
	END IF;
	
	IF mviews.typefacture = 'FACTURE' THEN
		_debit  := 0.0;
		_credit := mviews.montanttaxeparafiscale * _taux_usd_eur;
	END IF;
	IF mviews.typefacture = 'AVOIR' THEN
		_debit  := mviews.montanttaxeparafiscale * -1 * _taux_usd_eur;
		_credit := 0.0;
	END IF;
	
	_intitulecompte := 'TAXE PARAFISCALE';
	
	INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);

	-- INSERTION DE LA LIGNE ESCOMPTE
	IF mviews.montantescompte <> 0 THEN
		IF (_typevente = 1) THEN 
			_compte := '66500000';
		END IF;
		IF (_typevente = 2) THEN 
			_compte := '66502200';
		END IF;
		IF (_typevente = 3) THEN 
			_compte := '66508000';
		END IF;
		--_compte = '665000';
		IF mviews.typefacture = 'FACTURE' THEN
			_debit  := mviews.montantescompte * _taux_usd_eur;
			_credit := 0.0;
		END IF;
		IF mviews.typefacture = 'AVOIR' THEN
			_debit  := 0.0;
			_credit := mviews.montantescompte * -1 * _taux_usd_eur;
		END IF;
		
		_intitulecompte := 'ESCOMPTE';
		
		INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);
	END IF;

	-- INSERTION DE LA LIGNE REMISE
	IF mviews.montantremise <> 0 THEN
		IF _typevente = 1 THEN 
			_compte := '70910000';	-- TODO A VALIDER 
		END IF;
		IF _typevente = 2 THEN 
			--_compte := '701020';
			_compte := '70912000';
		END IF;
		IF _typevente = 3 THEN 
			_compte := '70918000';
		END IF;
		IF mviews.typefacture = 'FACTURE' THEN
			_debit  := mviews.montantremise * _taux_usd_eur;
			_credit := 0.0;
		END IF;
		IF mviews.typefacture = 'AVOIR' THEN
			_debit  := 0.0;
			_credit := mviews.montantremise * -1 * _taux_usd_eur;
		END IF;
	
		_intitulecompte := 'REMISE';
	
		INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);
	END IF;

	-- ANcien CoDE jamais mis en ligne
	--IF mviews.montantremise <> 0 THEN
	--	_compte = '709100';
	--	IF mviews.typefacture = 'FACTURE' THEN
	--		_debit  := mviews.montantremise;
	--		_credit := 0.0;
	--	END IF;
	--	IF mviews.typefacture = 'AVOIR' THEN
	--		_debit  := 0.0;
	--		_credit := mviews.montantremise;
	--	END IF;
		
	--	_intitulecompte := 'REMISE';
		
	--	INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);
	--END IF;

	
	
    END LOOP;

	-- TRAITEMENT DES FACTURES ACOMPTE
	FOR mviews IN SELECT datefacture, codefacture, typefacture, codetiers, ROUND(netcommercialht, 2) AS netcommercialht, ROUND(montantbruthtfacture, 2) AS montantbruthtfacture, 
							ROUND(montanttotalttcfacture, 2) AS montanttotalttcfacture, ROUND(montanttotaltvafacture, 2) AS montanttotaltvafacture, 
							ROUND(montanttaxeparafiscale, 2) AS montanttaxeparafiscale, 
							ROUND(fraisdeportht, 2) AS fraisdeportht, ROUND(montantescompte, 2) AS montantescompte, ROUND(montantremise, 2) AS montantremise, ROUND(montantacompteverse, 2) AS montantacompteverse,
							devisefacture
		   FROM facture__tbl 
		   WHERE datefacture BETWEEN _datedebut AND _datefin
		   AND typefacture = 'FACTURE ACOMPTE'
		   ORDER BY codefacture
		   LOOP
		   
	_codejournal := 'VE';

	SELECT categorietiers INTO _typevente FROM tiers__tbl WHERE codetiers = mviews.codetiers;
	
	SELECT comptetiers INTO _compte  FROM tiers__tbl WHERE codetiers = mviews.codetiers; 
	
	SELECT SUBSTR(raisonsocialetiers,0,25) INTO _libelle FROM tiers__tbl WHERE codetiers = mviews.codetiers; 

	SELECT COALESCE(1/ratetauxchangeusd, 1) INTO _taux_usd_eur FROM tauxchangeusd__tbl WHERE datetauxchangeusd = mviews.datefacture;

	IF _taux_usd_eur = NULL THEN
		_taux_usd_eur := 1.0;
	END IF;

	IF mviews.devisefacture != 'USD' THEN
		_taux_usd_eur := 1.0;
	END IF;
	
	
	IF mviews.typefacture = 'FACTURE ACOMPTE' THEN
		_debit  := mviews.montanttotalttcfacture * _taux_usd_eur;
		_credit := 0.0;
	END IF;
	
	_intitulecompte := _libelle;

 	-- INSERTION DE LA LIGNE DE VENTE									 
	INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);

	-- INSERTION DE LA LIGNE MONTANT HT
	IF _typevente = 1 THEN 
		_compte := '41910000';
	END IF;
	IF _typevente = 2 THEN 
		_compte := '41912000';
	END IF;
	IF _typevente = 3 THEN 
		_compte := '41918000 ';
	END IF;
	     
	IF mviews.typefacture = 'FACTURE ACOMPTE' THEN
		_debit  := 0.0;
		_credit := mviews.montantbruthtfacture * _taux_usd_eur;
	END IF;
	
	_intitulecompte := 'FACTURE ACOMPTE';
	
	INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);

	-- INSERTION DE LA LIGNE TVA	
	IF _typevente = 1 THEN 
		_compte := '44571100';
	END IF;
	IF _typevente = 2 THEN 
		_compte := '44571100';
	END IF;
	IF _typevente = 3 THEN 
		_compte := '44571100';
	END IF;

	IF mviews.typefacture = 'FACTURE ACOMPTE' THEN
		_debit  := 0.0;
		_credit := mviews.montanttotaltvafacture * _taux_usd_eur;
	END IF;
	
	_intitulecompte := 'TVA COLLECTEE';
	
	INSERT INTO tmpExportFacture(codejournal, date_facture, compte,  libelle, debit, credit, numeropiece, intitulecompte) VALUES (_codejournal, mviews.datefacture, _compte, _libelle, _debit, _credit, mviews.codefacture, _intitulecompte);

	
    END LOOP;

    DELETE FROM tmpExportFacture WHERE COALESCE(debit, 0) = 0 AND COALESCE(credit, 0) = 0;

    INSERT INTO tmpNewExportFacture(date_facture, codejournal, compte, debit, credit, libelle, numeropiece) SELECT date_facture, codejournal, compte, debit, credit, libelle, numeropiece FROM tmpExportFacture;

    --RETURN QUERY SELECT * FROM tmpExportFacture;
    RETURN QUERY SELECT * FROM tmpNewExportFacture;
	
END ;
$$;


ALTER FUNCTION public.exportcomptablefacture(_datedebut date, _datefin date) OWNER TO "JpA";

--
-- TOC entry 302 (class 1255 OID 25428)
-- Name: exportfacturedeb(date, date, character varying); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.exportfacturedeb(_datedebut date, _datefin date, _categorietiers character varying) RETURNS TABLE(_raisonsocialetiers character varying, _codefacture character varying, _netcommercialht numeric, _montantescompte numeric, _montanttaxeparafiscale numeric, _fraisdeportht numeric, _total numeric, _dummy bigint)
    LANGUAGE plpgsql
    AS $$

DECLARE
	_r record;
	_taux_usd_eur	numeric;

BEGIN
	DROP TABLE IF EXISTS tmpFacture;
	CREATE TEMPORARY TABLE tmpFacture (codefacture character varying(50),
					   codetiers character varying(50),
					   datefacture date,
					   devisefacture character varying(5),
					   netcommercialht numeric(10,5),
					   montantescompte numeric(10,5),
					   montanttaxeparafiscale numeric(10,2),
					   fraisdeportht numeric(10,5),
					   total numeric(10,5)
					  ) 
	ON commit DROP ;

	INSERT INTO tmpFacture(codefacture, codetiers, datefacture, devisefacture, netcommercialht, montantescompte, montanttaxeparafiscale, fraisdeportht, total )
	SELECT codefacture, T.codetiers, datefacture, devisefacture, netcommercialht, montantescompte, montanttaxeparafiscale, fraisdeportht,
	       (netcommercialht - montantescompte + montanttaxeparafiscale + fraisdeportht)
	FROM facture__tbl F, Tiers__tbl T
	WHERE  F.codetiers = T.codetiers
	AND F.datefacture BETWEEN _datedebut AND _datefin
	AND T.categorietiers = _categorietiers;

	FOR _r IN (SELECT * FROM tmpFacture) LOOP 
	
	    SELECT COALESCE(1/ratetauxchangeusd, 1) INTO _taux_usd_eur FROM tauxchangeusd__tbl WHERE datetauxchangeusd = _r.datefacture;

	    IF _taux_usd_eur = NULL THEN
		_taux_usd_eur := 1.0;
	    END IF;

	   IF _r.devisefacture != 'USD' THEN
		_taux_usd_eur := 1.0;
	   END IF;	    

	   UPDATE tmpFacture 
	   SET netcommercialht = netcommercialht * _taux_usd_eur,
	       montantescompte = montantescompte * _taux_usd_eur,
	       montanttaxeparafiscale = montanttaxeparafiscale * _taux_usd_eur,
	       fraisdeportht = fraisdeportht * _taux_usd_eur,
	       total = total * _taux_usd_eur
	   WHERE codefacture = _r.codefacture; 

	END LOOP; 	

	RETURN QUERY SELECT T.RAISONSOCIALETIERS, F.CODEFACTURE, F.netcommercialht, F.montantescompte, F.montanttaxeparafiscale, F.fraisdeportht, F.total, 0
			FROM tmpFacture F, TIERS__TBL T
			WHERE F.CODETIERS = T.CODETIERS
		UNION
			SELECT T.RAISONSOCIALETIERS, 'TOTAL', SUM(F.netcommercialht), SUM(F.montantescompte), SUM(F.montanttaxeparafiscale), SUM(F.fraisdeportht), SUM(F.total), COUNT(T.RAISONSOCIALETIERS)
			FROM tmpFacture F, TIERS__TBL T
			WHERE F.CODETIERS = T.CODETIERS
			GROUP BY T.RAISONSOCIALETIERS
			HAVING COUNT(T.RAISONSOCIALETIERS) > 1
		UNION
			SELECT 'ZZZZZZZZZZ' AS RAISONSOCIALETIERS, '', SUM(F.netcommercialht), SUM(F.montantescompte), SUM(F.montanttaxeparafiscale), SUM(F.fraisdeportht), SUM(F.total) AS total, 0
			FROM tmpFacture F, TIERS__TBL T
			WHERE F.CODETIERS = T.CODETIERS
			ORDER BY RAISONSOCIALETIERS, CODEFACTURE;
END ;
$$;


ALTER FUNCTION public.exportfacturedeb(_datedebut date, _datefin date, _categorietiers character varying) OWNER TO "JpA";

--
-- TOC entry 303 (class 1255 OID 25429)
-- Name: factureclienttransformationenavoir(character varying); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.factureclienttransformationenavoir(_codefacture character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$ 

DECLARE
  _strcodefacture character varying (50);
  _codetiers character varying (50);
   
BEGIN
    _strcodefacture := (SELECT generecodefacturationclient());

    SELECT codetiers INTO _codetiers FROM Facture__tbl WHERE codefacture = _codefacture;

    INSERT INTO Facture__tbl ( codefacture, typefacture, codetiers, libellefacture, saisonfacture, codelivraison, codeetat, 
				 datefacture, raisonsocialetiers, adresse1tiers, adresse2tiers, codepostaltiers, villetiers, paystiers,
				 telephone1tiers, telephone2tiers, telephone3tiers, telecopietiers, adressedemessagerietiers, numerosirettiers, tauxtaxeparafiscale, tauxtvataxeparafiscale,
				fraisdeportht, tauxtvafraisdeport, pourcentageescompte,  montantescompte, montanttvaescompte, montantacompteverse, devisefacture, languedecorrespondance, interlocuteur, avoirsurarticle, origine) 
    SELECT _strcodefacture, 'AVOIR', codetiers, 'AVOIR SUR LA FACTURE N° ' || _codefacture, saisonfacture, codelivraison, codeetat, now(), 
           raisonsocialetiers, adresse1tiers, adresse2tiers, codepostaltiers, villetiers, paystiers,
           telephone1tiers, telephone2tiers, telephone3tiers, telecopietiers, adressedemessagerietiers, numerosirettiers, tauxtaxeparafiscale, tauxtvataxeparafiscale,
           fraisdeportht, tauxtvafraisdeport, pourcentageescompte,  montantescompte, montanttvaescompte, montantacompteverse, devisefacture, languedecorrespondance, interlocuteur, true, origine

    FROM Facture__tbl
    WHERE codefacture = _codefacture;

   INSERT INTO FactureLigne__tbl ( codefacture, typefacture, codelivraison, compteurlivraisonligne, codearticle, modelearticle, designationarticle, colorisarticle, 
                                      nbarticletailleunique, nbarticletailles, nbarticletaillem, nbarticletaillel, nbarticletaillexl, nbarticletaillexxl, nbarticle,
                                      prixdeventehtarticle, tauxtvaarticle, prixdeventettcarticle ) 
    SELECT _strcodefacture, 'AVOIR', codelivraison, compteurlivraisonligne, codearticle, modelearticle, designationarticle, colorisarticle, 
           nbarticletailleunique * -1, nbarticletailles * -1, nbarticletaillem * -1, nbarticletaillel * -1, nbarticletaillexl * -1, nbarticletaillexxl * -1, nbarticle * -1, 
           prixdeventehtarticle, tauxtvaarticle, prixdeventettcarticle 
    FROM FactureLigne__tbl 
    WHERE codefacture = _codefacture
    ORDER BY compteurfactureligne;
 
RETURN _strcodefacture;
END;
$$;


ALTER FUNCTION public.factureclienttransformationenavoir(_codefacture character varying) OWNER TO "JpA";

--
-- TOC entry 304 (class 1255 OID 25430)
-- Name: ftrgafterdeletecommande(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgafterdeletecommande() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
BEGIN
  DELETE FROM commandeligne__tbl WHERE codecommande = OLD.codecommande;
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgafterdeletecommande() OWNER TO "JpA";

--
-- TOC entry 305 (class 1255 OID 25431)
-- Name: ftrgafterdeletefacture(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgafterdeletefacture() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
BEGIN
  DELETE FROM factureligne__tbl WHERE codefacture = OLD.codefacture AND typefacture = OLD.typefacture;
  UPDATE parametre__tbl SET prochaincodefacture = prochaincodefacture - 1 WHERE compteurparametre = 1;
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgafterdeletefacture() OWNER TO "JpA";

--
-- TOC entry 306 (class 1255 OID 25432)
-- Name: ftrgafterdeletelivraison(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgafterdeletelivraison() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
BEGIN
  DELETE FROM livraisonligne__tbl WHERE codelivraison = OLD.codelivraison;
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgafterdeletelivraison() OWNER TO "JpA";

--
-- TOC entry 283 (class 1255 OID 25433)
-- Name: ftrgafterinsertorupdatefacture(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgafterinsertorupdatefacture() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE') ) THEN 

	NEW.montanttvafraisdeport := NEW.fraisdeportht * ( 1 + NEW.tauxtvafraisdeport/100.0 );

	NEW.montanttotalhtfacture := NEW.montantbruthtfacture + COALESCE(NEW.fraisdeportht, 0);

	NEW.montanttotaltvafacture = NEW.montantbruttvafacture * COALESCE(NEW.montanttvafraisdeport, 0);

	NEW.montanttotalttcfacture = NEW.montanttotalhtfacture + NEW.montanttotaltvafacture;
	
  END IF;
  
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgafterinsertorupdatefacture() OWNER TO "JpA";

--
-- TOC entry 285 (class 1255 OID 25434)
-- Name: ftrgafterinsertorupdatelivraison(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgafterinsertorupdatelivraison() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE') ) THEN 

	NEW.montanttvafraisdeport := NEW.fraisdeportht * ( 1 + NEW.tauxtvafraisdeport/100.0 );

	NEW.montanttotalhtlivraison := NEW.montantbruthtlivraison + COALESCE(NEW.fraisdeportht, 0);

	NEW.montanttotaltvalivraison = NEW.montantbruttvalivraison * COALESCE(NEW.montanttvafraisdeport, 0);

	NEW.montanttotalttclivraison = NEW.montanttotalhtlivraison + NEW.montanttotaltvalivraison;
	
  END IF;
  
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgafterinsertorupdatelivraison() OWNER TO "JpA";

--
-- TOC entry 307 (class 1255 OID 25435)
-- Name: ftrgafterinsertorupdateordeletecommandeligne(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgafterinsertorupdateordeletecommandeligne() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE') ) THEN 
	UPDATE commande__tbl SET nbarticlecommande        = (SELECT SUM(COALESCE(nbarticle, 0))         FROM commandeligne__tbl WHERE codecommande = NEW.codecommande) WHERE codecommande = NEW.codecommande;
	UPDATE commande__tbl SET montantbruthtcommande    = (SELECT SUM(COALESCE(montanttotalht, 0.0))  FROM commandeligne__tbl WHERE codecommande = NEW.codecommande) WHERE codecommande = NEW.codecommande;
	UPDATE commande__tbl SET montantbruttvacommande  = (SELECT SUM(COALESCE(montanttotaltva, 0.0)) FROM commandeligne__tbl WHERE codecommande = NEW.codecommande) WHERE codecommande = NEW.codecommande;
	--UPDATE commande__tbl SET montanttotalttccommande  = (SELECT SUM(COALESCE(montanttotalttc, 0.0)) FROM commandeligne__tbl WHERE codecommande = NEW.codecommande) WHERE codecommande = NEW.codecommande;
  END IF;
  
  IF ( (TG_OP = 'DELETE') ) THEN  
	UPDATE commande__tbl SET nbarticlecommande        = (SELECT SUM(COALESCE(nbarticle, 0))         FROM commandeligne__tbl WHERE codecommande = OLD.codecommande) WHERE codecommande = OLD.codecommande;
	UPDATE commande__tbl SET montantbruthtcommande    = (SELECT SUM(COALESCE(montanttotalht, 0.0))  FROM commandeligne__tbl WHERE codecommande = OLD.codecommande) WHERE codecommande = OLD.codecommande;
	UPDATE commande__tbl SET montantbruttvacommande  = (SELECT SUM(COALESCE(montanttotaltva, 0.0)) FROM commandeligne__tbl WHERE codecommande = OLD.codecommande) WHERE codecommande = OLD.codecommande;
	--UPDATE commande__tbl SET montanttotalttccommande  = (SELECT SUM(COALESCE(montanttotalttc, 0.0)) FROM commandeligne__tbl WHERE codecommande = OLD.codecommande) WHERE codecommande = OLD.codecommande;
  END IF;
 
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgafterinsertorupdateordeletecommandeligne() OWNER TO "JpA";

--
-- TOC entry 308 (class 1255 OID 25436)
-- Name: ftrgafterinsertorupdateordeletecommandemodalitedereglement(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgafterinsertorupdateordeletecommandemodalitedereglement() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE') ) THEN 
	UPDATE commande__tbl SET montantmodalitedereglement = (SELECT SUM(COALESCE(montant, 0)) FROM commandemodalitedereglement__tbl WHERE codecommande = NEW.codecommande) WHERE codecommande = NEW.codecommande;
  END IF;
  
  IF ( (TG_OP = 'DELETE') ) THEN  
	UPDATE commande__tbl SET montantmodalitedereglement  = (SELECT SUM(COALESCE(montant, 0)) FROM commandemodalitedereglement__tbl WHERE codecommande = OLD.codecommande) WHERE codecommande = OLD.codecommande;
  END IF;
 
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgafterinsertorupdateordeletecommandemodalitedereglement() OWNER TO "JpA";

--
-- TOC entry 309 (class 1255 OID 25437)
-- Name: ftrgafterinsertorupdateordeletefactureligne(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgafterinsertorupdateordeletefactureligne() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE') ) THEN 
	UPDATE facture__tbl SET nbarticlefacture        = (SELECT SUM(COALESCE(nbarticle, 0))         FROM factureligne__tbl WHERE codefacture = NEW.codefacture AND typefacture = NEW.typefacture) WHERE codefacture = NEW.codefacture AND typefacture = NEW.typefacture;
	UPDATE facture__tbl SET montantbruthtfacture    = (SELECT SUM(COALESCE(montanttotalht, 0.0))  FROM factureligne__tbl WHERE codefacture = NEW.codefacture AND typefacture = NEW.typefacture) WHERE codefacture = NEW.codefacture AND typefacture = NEW.typefacture;
	UPDATE facture__tbl SET montantbruttvafacture   = (SELECT SUM(COALESCE(montanttotaltva, 0.0)) FROM factureligne__tbl WHERE codefacture = NEW.codefacture AND typefacture = NEW.typefacture) WHERE codefacture = NEW.codefacture AND typefacture = NEW.typefacture;
  END IF;
  
  IF ( (TG_OP = 'DELETE') ) THEN  
	UPDATE facture__tbl SET nbarticlefacture        = (SELECT SUM(COALESCE(nbarticle, 0))         FROM factureligne__tbl WHERE codefacture = OLD.codefacture AND typefacture = OLD.typefacture) WHERE codefacture = OLD.codefacture AND typefacture = OLD.typefacture;
	UPDATE facture__tbl SET montantbruthtfacture    = (SELECT SUM(COALESCE(montanttotalht, 0.0))  FROM factureligne__tbl WHERE codefacture = OLD.codefacture AND typefacture = OLD.typefacture) WHERE codefacture = OLD.codefacture AND typefacture = OLD.typefacture;
	UPDATE facture__tbl SET montantbruttvafacture   = (SELECT SUM(COALESCE(montanttotaltva, 0.0)) FROM factureligne__tbl WHERE codefacture = OLD.codefacture AND typefacture = OLD.typefacture) WHERE codefacture = OLD.codefacture AND typefacture = OLD.typefacture;
  END IF;
 
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgafterinsertorupdateordeletefactureligne() OWNER TO "JpA";

--
-- TOC entry 310 (class 1255 OID 25438)
-- Name: ftrgafterinsertorupdateordeletelivraisonligne(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgafterinsertorupdateordeletelivraisonligne() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE') ) THEN 
	UPDATE livraison__tbl SET nbarticlelivraison        = (SELECT SUM(COALESCE(nbarticle, 0))         FROM livraisonligne__tbl WHERE codelivraison = NEW.codelivraison) WHERE codelivraison = NEW.codelivraison;
	UPDATE livraison__tbl SET montantbruthtlivraison    = (SELECT SUM(COALESCE(montanttotalht, 0.0))  FROM livraisonligne__tbl WHERE codelivraison = NEW.codelivraison) WHERE codelivraison = NEW.codelivraison;
	UPDATE livraison__tbl SET montantbruttvalivraison   = (SELECT SUM(COALESCE(montanttotaltva, 0.0)) FROM livraisonligne__tbl WHERE codelivraison = NEW.codelivraison) WHERE codelivraison = NEW.codelivraison;
	--UPDATE livraison__tbl SET montanttotalttclivraison  = (SELECT SUM(COALESCE(montanttotalttc, 0.0)) FROM livraisonligne__tbl WHERE codelivraison = NEW.codelivraison) WHERE codelivraison = NEW.codelivraison;
  END IF;
  
  IF ( (TG_OP = 'DELETE') ) THEN  
	UPDATE livraison__tbl SET nbarticlelivraison        = (SELECT SUM(COALESCE(nbarticle, 0))         FROM livraisonligne__tbl WHERE codelivraison = OLD.codelivraison) WHERE codelivraison = OLD.codelivraison;
	UPDATE livraison__tbl SET montantbruthtlivraison    = (SELECT SUM(COALESCE(montanttotalht, 0.0))  FROM livraisonligne__tbl WHERE codelivraison = OLD.codelivraison) WHERE codelivraison = OLD.codelivraison;
	UPDATE livraison__tbl SET montantbruttvalivraison   = (SELECT SUM(COALESCE(montanttotaltva, 0.0)) FROM livraisonligne__tbl WHERE codelivraison = OLD.codelivraison) WHERE codelivraison = OLD.codelivraison;
	--UPDATE livraison__tbl SET montanttotalttclivraison  = (SELECT SUM(COALESCE(montanttotalttc, 0.0)) FROM livraisonligne__tbl WHERE codelivraison = OLD.codelivraison) WHERE codelivraison = OLD.codelivraison;
  END IF;
 
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgafterinsertorupdateordeletelivraisonligne() OWNER TO "JpA";

--
-- TOC entry 311 (class 1255 OID 25439)
-- Name: ftrgafterinsertorupdateordeletereglementclient(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgafterinsertorupdateordeletereglementclient() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE') ) THEN 
	UPDATE commande__tbl SET montantreglement = (SELECT SUM(COALESCE(montantreglementclient, 0)) FROM reglementclient__tbl WHERE codecommande = NEW.codecommande) WHERE codecommande = NEW.codecommande;
  END IF;
  
  IF ( (TG_OP = 'DELETE') ) THEN  
	UPDATE commande__tbl SET montantreglement  = (SELECT SUM(COALESCE(montantreglementclient, 0)) FROM reglementclient__tbl WHERE codecommande = OLD.codecommande) WHERE codecommande = OLD.codecommande;
  END IF;
 
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgafterinsertorupdateordeletereglementclient() OWNER TO "JpA";

--
-- TOC entry 312 (class 1255 OID 25440)
-- Name: ftrgbeforeinsertorupdateagenda(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgbeforeinsertorupdateagenda() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN
  IF ( TG_OP = 'INSERT' ) THEN  
    NEW.compteuragenda := (select max(compteuragenda) from agenda__tbl);
    IF (NEW.compteuragenda IS NULL) THEN
      NEW.compteuragenda := 1;
    ELSE
      NEW.compteuragenda := NEW.compteuragenda + 1;
    END IF;
    NEW.datecreationagenda := 'now';
  END IF;

  IF ( TG_OP = 'UPDATE' ) THEN
    NEW.datedernieremodificationagenda := 'now';
  END IF;  

  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgbeforeinsertorupdateagenda() OWNER TO "JpA";

--
-- TOC entry 286 (class 1255 OID 25441)
-- Name: ftrgbeforeinsertorupdatearticle(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgbeforeinsertorupdatearticle() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
  _codearticle smallint;

BEGIN
  
  IF ( TG_OP = 'INSERT' ) THEN  

    UPDATE parametre__tbl SET derniercodearticle = derniercodearticle + 1 WHERE compteurparametre = 1;

    _codearticle := ( SELECT derniercodearticle FROM parametre__tbl WHERE compteurparametre = 1 );

    NEW.codearticle := lpad(_codearticle::text, 6, '0');

    NEW.DateCreationarticle := 'now';
  END IF;

  IF ( TG_OP = 'UPDATE' ) THEN
    NEW.datedernieremodificationarticle := 'now';
  END IF;  

  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgbeforeinsertorupdatearticle() OWNER TO "JpA";

--
-- TOC entry 287 (class 1255 OID 25442)
-- Name: ftrgbeforeinsertorupdatecommande(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgbeforeinsertorupdatecommande() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
 -- _codecommande smallint;
 -- _nb smallint;
 -- _montanttvaescompte numeric(10, 5);
  

BEGIN
  
  IF ( TG_OP = 'INSERT' ) THEN  
    update parametre__tbl set prochaincodecommande = prochaincodecommande + 1 where compteurparametre = 1;
    NEW.DateCreationcommande := 'now';
  END IF;

  IF ( TG_OP = 'UPDATE' ) THEN
    NEW.datedernieremodificationcommande := 'now';
  END IF;  

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE') ) THEN 

	NEW.montanttvafraisdeport      := NEW.fraisdeportht          * ( NEW.tauxtvafraisdeport/100.0 );

	NEW.montanttaxeparafiscale     := NEW.montantbruthtcommande  * ( NEW.tauxtaxeparafiscale/100.0 );

	NEW.montanttvataxeparafiscale  := NEW.montanttaxeparafiscale * ( NEW.tauxtvataxeparafiscale/100.0 );

	NEW.montanttotalhtcommande     := NEW.montantbruthtcommande  + COALESCE(NEW.fraisdeportht, 0) + COALESCE(NEW.montanttaxeparafiscale, 0);

	NEW.montanttotalhtcommandeenvisage := NEW.montanttotalhtcommande;

	

	NEW.montantescompte    	       := NEW.montanttotalhtcommande * ( COALESCE(NEW.pourcentageescompte, 0)/100.0 );

	NEW.montantescompteenvisage    := NEW.montanttotalhtcommande * ( COALESCE(NEW.pourcentageescompteenvisage, 0)/100.0 );

	NEW.montanttotalhtcommande     := NEW.montanttotalhtcommande - NEW.montantescompte;

	NEW.montanttotalhtcommandeenvisage     := NEW.montanttotalhtcommandeenvisage - NEW.montantescompteenvisage;


	NEW.montanttvaescompte	       := (SELECT SUM(montanttvaescompte) FROM montant_tva_escompte_par_commande(NEW.codecommande, NEW.pourcentageescompte)) + 
					 ( COALESCE(NEW.montanttvafraisdeport, 0) * NEW.pourcentageescompte / 100.0 ) + ( COALESCE(NEW.montanttvataxeparafiscale, 0) * NEW.pourcentageescompte / 100.0 );

	NEW.montanttvaescompteenvisage := (SELECT SUM(montanttvaescompte) FROM montant_tva_escompte_par_commande(NEW.codecommande, NEW.pourcentageescompteenvisage)) + 
					 ( COALESCE(NEW.montanttvafraisdeport, 0) * NEW.pourcentageescompteenvisage / 100.0 ) + ( COALESCE(NEW.montanttvataxeparafiscale, 0) * NEW.pourcentageescompteenvisage / 100.0 );


	NEW.montanttotaltvacommande    := NEW.montantbruttvacommande  + COALESCE(NEW.montanttvafraisdeport, 0) + COALESCE(NEW.montanttvataxeparafiscale, 0) - NEW.montanttvaescompte;

	NEW.montanttotaltvacommandeenvisage    := NEW.montantbruttvacommande  + COALESCE(NEW.montanttvafraisdeport, 0) + COALESCE(NEW.montanttvataxeparafiscale, 0) - NEW.montanttvaescompteenvisage;


	NEW.montanttotalttccommande    := NEW.montanttotalhtcommande  + NEW.montanttotaltvacommande - COALESCE(NEW.montantacompteverse, 0);

	NEW.montanttotalttccommandeenvisage    := NEW.montanttotalhtcommandeenvisage  + NEW.montanttotaltvacommandeenvisage;

  END IF;
  
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgbeforeinsertorupdatecommande() OWNER TO "JpA";

--
-- TOC entry 313 (class 1255 OID 25443)
-- Name: ftrgbeforeinsertorupdatecommandeligne(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgbeforeinsertorupdatecommandeligne() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE')   ) THEN 
	NEW.nbarticle      := COALESCE(NEW.nbarticletailleunique, 0) + COALESCE(NEW.nbarticletailles, 0) + COALESCE(NEW.nbarticletaillem, 0) + COALESCE(NEW.nbarticletaillel, 0) + COALESCE(NEW.nbarticletaillexl, 0) + COALESCE(NEW.nbarticletaillexxl, 0); 
	NEW.nbarticlelivre := COALESCE(NEW.nbarticletailleuniquelivre, 0) + COALESCE(NEW.nbarticletailleslivre, 0) + COALESCE(NEW.nbarticletaillemlivre, 0) + COALESCE(NEW.nbarticletaillellivre, 0) + COALESCE(NEW.nbarticletaillexllivre, 0) + COALESCE(NEW.nbarticletaillexxllivre, 0); 
	NEW.montanttotalht := NEW.nbarticle * COALESCE(NEW.prixdeventehtarticle, 0);
	IF ( COALESCE(NEW.tauxtvaarticle, 0.0) != 0.0 ) THEN
		NEW.prixdeventettcarticle := NEW.prixdeventehtarticle * ( 1 + NEW.tauxtvaarticle/100.0 );
	ELSE
		NEW.prixdeventettcarticle := NEW.prixdeventehtarticle;
	END IF;
	NEW.montanttotalttc = NEW.nbarticle * COALESCE(NEW.prixdeventettcarticle, 0);
	NEW.montanttotaltva = NEW.montanttotalttc - NEW.montanttotalht;
	
	IF (TG_OP = 'INSERT') THEN 
		NEW.DateCreationcommandeligne := 'now';
	END IF;
	IF (TG_OP = 'UPDATE') THEN 
		NEW.Datedernieremodificationcommandeligne := 'now';
	END IF;
  END IF;

  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgbeforeinsertorupdatecommandeligne() OWNER TO "JpA";

--
-- TOC entry 326 (class 1255 OID 25444)
-- Name: ftrgbeforeinsertorupdatefacture(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgbeforeinsertorupdatefacture() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
  _codefacture smallint;
  _nb smallint;

BEGIN
  
  IF ( TG_OP = 'INSERT' ) THEN  
    IF ( NEW.typefacture = 'FACTURE' ) OR ( NEW.typefacture = 'AVOIR' )  OR ( NEW.typefacture = 'FACTURE ACOMPTE' ) THEN
	update parametre__tbl set prochaincodefacture = prochaincodefacture + 1 where compteurparametre = 1;
    END IF;
    IF ( NEW.typefacture = 'ACOMPTE' ) THEN
	update parametre__tbl set prochaincodefactureacompte = prochaincodefactureacompte + 1 where compteurparametre = 1;
    END IF;
    NEW.DateCreationfacture := 'now';
  END IF;

  IF ( TG_OP = 'UPDATE' ) THEN
    NEW.datedernieremodificationfacture := 'now';
  END IF;  

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE') ) THEN 

	NEW.montantremise	      := NEW.montantbruthtfacture   * ( COALESCE(NEW.pourcentageremise, 0)/100.0 );

	NEW.netcommercialht           := NEW.montantbruthtfacture - NEW.montantremise;

	NEW.montanttvafraisdeport     := NEW.fraisdeportht     * ( NEW.tauxtvafraisdeport/100.0 );

	NEW.montanttaxeparafiscale    := NEW.netcommercialht   * ( NEW.tauxtaxeparafiscale/100.0 );

	NEW.montanttvataxeparafiscale := NEW.montanttaxeparafiscale * ( NEW.tauxtvataxeparafiscale/100.0 );

	NEW.montanttotalhtfacture     := NEW.netcommercialht   + COALESCE(NEW.fraisdeportht, 0) + COALESCE(NEW.montanttaxeparafiscale, 0);

	NEW.montantescompte    	      := NEW.montanttotalhtfacture * ( COALESCE(NEW.pourcentageescompte, 0)/100.0 );
	
	NEW.montanttotalhtfacture     := NEW.montanttotalhtfacture - NEW.montantescompte;

	NEW.montanttvaescompte	      := (SELECT SUM(montanttvaescompte) FROM montant_tva_escompte_par_facture(NEW.codefacture, NEW.pourcentageescompte, NEW.pourcentageremise)) + 
					 ( COALESCE(NEW.montanttvafraisdeport, 0) * NEW.pourcentageescompte / 100.0 ) + ( COALESCE(NEW.montanttvataxeparafiscale, 0) * NEW.pourcentageescompte / 100.0 );
	
	NEW.montanttotaltvafacture    := (SELECT SUM(montanttvafacture) FROM montant_tva_par_facture(NEW.codefacture, NEW.pourcentageremise))  + COALESCE(NEW.montanttvafraisdeport, 0) + COALESCE(NEW.montanttvataxeparafiscale, 0) - NEW.montanttvaescompte;

	NEW.montanttotalttcfacture    := NEW.montanttotalhtfacture  + NEW.montanttotaltvafacture - COALESCE(NEW.montantacompteverse, 0);

	NEW.montantacompteverseht     := COALESCE(NEW.montantacompteverse, 0.0) / ( 1.0 + COALESCE(NEW.tauxtvaacompteverse, 0.0) / 100.0);

	NEW.montanttvaacompteverse    := COALESCE(NEW.montantacompteverse, 0.0) - COALESCE(NEW.montantacompteverseht, 0.0);
  END IF;
  
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgbeforeinsertorupdatefacture() OWNER TO "JpA";

--
-- TOC entry 314 (class 1255 OID 25445)
-- Name: ftrgbeforeinsertorupdatefactureligne(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgbeforeinsertorupdatefactureligne() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE')   ) THEN 
	NEW.nbarticle      := COALESCE(NEW.nbarticletailleunique, 0) + COALESCE(NEW.nbarticletailles, 0) + COALESCE(NEW.nbarticletaillem, 0) + COALESCE(NEW.nbarticletaillel, 0) + COALESCE(NEW.nbarticletaillexl, 0) + COALESCE(NEW.nbarticletaillexxl, 0); 
	NEW.montanttotalht := NEW.nbarticle * COALESCE(NEW.prixdeventehtarticle, 0);
	NEW.prixdeventettcarticle := NEW.prixdeventehtarticle * ( 1 + NEW.tauxtvaarticle/100.0 );
	NEW.montanttotalttc = NEW.nbarticle * COALESCE(NEW.prixdeventettcarticle, 0);
	NEW.montanttotaltva = NEW.montanttotalttc - NEW.montanttotalht;
	
	IF (TG_OP = 'INSERT') THEN 
		NEW.DateCreationfactureligne := 'now';
	END IF;
	IF (TG_OP = 'UPDATE') THEN 
		NEW.Datedernieremodificationfactureligne := 'now';
	END IF;
  END IF;

  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgbeforeinsertorupdatefactureligne() OWNER TO "JpA";

--
-- TOC entry 315 (class 1255 OID 25446)
-- Name: ftrgbeforeinsertorupdatelivraison(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgbeforeinsertorupdatelivraison() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
  _codelivraison smallint;
  _nb smallint;

BEGIN
  
  IF ( TG_OP = 'INSERT' ) THEN  
    update parametre__tbl set prochaincodelivraison = prochaincodelivraison + 1 where compteurparametre = 1;
    NEW.DateCreationlivraison := 'now';
  END IF;

  IF ( TG_OP = 'UPDATE' ) THEN
    NEW.datedernieremodificationlivraison := 'now';
  END IF;  

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE') ) THEN 

	NEW.montanttvafraisdeport := NEW.fraisdeportht * ( NEW.tauxtvafraisdeport/100.0 );

	NEW.montanttaxeparafiscale    := NEW.montantbruthtlivraison   * ( NEW.tauxtaxeparafiscale/100.0 );

	NEW.montanttvataxeparafiscale := NEW.montanttaxeparafiscale * ( NEW.tauxtvataxeparafiscale/100.0 );

	NEW.montanttotalhtlivraison := NEW.montantbruthtlivraison + COALESCE(NEW.fraisdeportht, 0) + COALESCE(NEW.montanttaxeparafiscale, 0);

	NEW.montanttotaltvalivraison = NEW.montantbruttvalivraison + COALESCE(NEW.montanttvafraisdeport, 0) + COALESCE(NEW.montanttvataxeparafiscale, 0) ;

	NEW.montanttotalttclivraison = NEW.montanttotalhtlivraison + NEW.montanttotaltvalivraison;
	
  END IF;
  
  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgbeforeinsertorupdatelivraison() OWNER TO "JpA";

--
-- TOC entry 316 (class 1255 OID 25447)
-- Name: ftrgbeforeinsertorupdatelivraisonligne(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgbeforeinsertorupdatelivraisonligne() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN

  IF ( (TG_OP = 'INSERT') OR  (TG_OP = 'UPDATE')   ) THEN 
	NEW.nbarticle      := COALESCE(NEW.nbarticletailleunique, 0) + COALESCE(NEW.nbarticletailles, 0) + COALESCE(NEW.nbarticletaillem, 0) + COALESCE(NEW.nbarticletaillel, 0) + COALESCE(NEW.nbarticletaillexl, 0) + COALESCE(NEW.nbarticletaillexxl, 0); 
	NEW.montanttotalht := NEW.nbarticle * COALESCE(NEW.prixdeventehtarticle, 0);
	NEW.prixdeventettcarticle := NEW.prixdeventehtarticle * ( 1 + NEW.tauxtvaarticle/100.0 );
	NEW.montanttotalttc = NEW.nbarticle * COALESCE(NEW.prixdeventettcarticle, 0);
	NEW.montanttotaltva = NEW.montanttotalttc - NEW.montanttotalht;
	
	IF (TG_OP = 'INSERT') THEN 
		NEW.DateCreationlivraisonligne := 'now';
	END IF;
	IF (TG_OP = 'UPDATE') THEN 
		NEW.Datedernieremodificationlivraisonligne := 'now';
	END IF;
  END IF;

  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgbeforeinsertorupdatelivraisonligne() OWNER TO "JpA";

--
-- TOC entry 317 (class 1255 OID 25448)
-- Name: ftrgbeforeinsertorupdatepersonnel(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgbeforeinsertorupdatepersonnel() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE

BEGIN
  
  IF ( TG_OP = 'INSERT' ) THEN  
    -- REGLE DE CALCUL
    -- En mode Creation, on insere les autorisations d'accés minimales
    -- Tous les Menu de niveau 0
    INSERT INTO AutorisationAcces__tbl(codepersonnel, CompteurMenu, autorise) SELECT NEW.codepersonnel, compteurMenu, menupardefaut FROM menuapp__tbl;-- WHERE compteurMenuPere = 0;
   -- INSERT INTO AutorisationAcces__tbl(codepersonnel, CompteurMenu) SELECT NEW.codepersonnel, 2;
  --  INSERT INTO AutorisationAcces__tbl(codepersonnel, CompteurMenu) SELECT NEW.codepersonnel, 12;

    NEW.datecreationpersonnel := 'now';
  END IF;

  IF ( TG_OP = 'UPDATE' ) THEN
    NEW.datedernieremodificationpersonnel := 'now';
  END IF;  

  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgbeforeinsertorupdatepersonnel() OWNER TO "JpA";

--
-- TOC entry 318 (class 1255 OID 25449)
-- Name: ftrgbeforeinsertorupdatetiers(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.ftrgbeforeinsertorupdatetiers() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
  _codetiers smallint;
  _nb smallint;

BEGIN
  
  IF ( TG_OP = 'INSERT' ) THEN 
   
    update parametre__tbl set derniercodetiers = derniercodetiers + 1 where compteurparametre = 1;

    NEW.DateCreationtiers := 'now';

  END IF;

  IF ( TG_OP = 'UPDATE' ) THEN
    NEW.datedernieremodificationtiers := 'now';
  END IF;  

  -- Insertion automatique
  IF NEW.titretiers IS NOT NULL THEN
    _nb := (SELECT COUNT(*) FROM codefils__tbl WHERE codepere = 'CIVILITE' AND libellecodefils = NEW.titretiers);

    IF ( _nb = 0 ) THEN 
      INSERT INTO codefils__tbl ( codepere, libellecodefils ) VALUES ( 'CIVILITE', NEW.titretiers ); 
    END IF;
  END IF;

  RETURN NEW;
END;
$$;


ALTER FUNCTION public.ftrgbeforeinsertorupdatetiers() OWNER TO "JpA";

--
-- TOC entry 319 (class 1255 OID 25450)
-- Name: generecodefacturationclient(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.generecodefacturationclient() RETURNS character varying
    LANGUAGE plpgsql
    AS $$ 

DECLARE
  _strcodefacturation character varying (50);
  _prochaincodefacture smallint;

BEGIN
  SELECT prochaincodefacture INTO _prochaincodefacture FROM Parametre__tbl WHERE compteurParametre = 1;

  _strcodefacturation = lpad(_prochaincodefacture::text, 6, '0');

RETURN _strcodefacturation;
END;
$$;


ALTER FUNCTION public.generecodefacturationclient() OWNER TO "JpA";

--
-- TOC entry 288 (class 1255 OID 25451)
-- Name: generecodelivraisonclient(); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.generecodelivraisonclient() RETURNS character varying
    LANGUAGE plpgsql
    AS $$ 

DECLARE
  _strcodelivraison character varying (50);
  _prochaincodelivraison smallint;

BEGIN
  SELECT prochaincodelivraison INTO _prochaincodelivraison FROM Parametre__tbl WHERE compteurParametre = 1;

  _strcodelivraison = lpad(_prochaincodelivraison::text, 6, '0');

RETURN _strcodelivraison;
END;
$$;


ALTER FUNCTION public.generecodelivraisonclient() OWNER TO "JpA";

--
-- TOC entry 320 (class 1255 OID 25452)
-- Name: matrice(character varying); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.matrice(character varying, OUT numero integer, OUT modelearticle character varying, OUT colorisarticle character varying, OUT nbarticletotal bigint, OUT nbarticletailles bigint, OUT nbarticletaillel bigint, OUT nbarticletaillexl bigint, OUT nbarticletaillem bigint, OUT nbarticletaillexxl bigint, OUT nbarticletailleunique bigint) RETURNS SETOF record
    LANGUAGE sql
    AS $_$  

SELECT ( SELECT modele__tbl.numero
           FROM modele__tbl
          WHERE modele__tbl.modele = a.modelearticle AND modele__tbl.saison = a.saisonarticle) AS numero, a.modelearticle, a.colorisarticle, ( SELECT sum(COALESCE(commandeligne__tbl.nbarticletailles, 0) + COALESCE(commandeligne__tbl.nbarticletaillel, 0) + COALESCE(commandeligne__tbl.nbarticletaillem, 0) + COALESCE(commandeligne__tbl.nbarticletaillexl, 0) + COALESCE(commandeligne__tbl.nbarticletaillexxl, 0)) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletotal, ( SELECT COALESCE(sum(commandeligne__tbl.nbarticletailles), 0::bigint) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletailles, ( SELECT sum(commandeligne__tbl.nbarticletaillel) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletaillel, ( SELECT sum(commandeligne__tbl.nbarticletaillexl) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletaillexl, ( SELECT sum(commandeligne__tbl.nbarticletaillem) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletaillem, ( SELECT sum(commandeligne__tbl.nbarticletaillexxl) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletaillexxl, ( SELECT sum(commandeligne__tbl.nbarticletailleunique) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletailleunique
   FROM article__tbl a
  WHERE a.saisonarticle = $1
  ORDER BY ( SELECT modele__tbl.numero
           FROM modele__tbl
          WHERE modele__tbl.modele = a.modelearticle AND modele__tbl.saison = a.saisonarticle), a.modelearticle; 

$_$;


ALTER FUNCTION public.matrice(character varying, OUT numero integer, OUT modelearticle character varying, OUT colorisarticle character varying, OUT nbarticletotal bigint, OUT nbarticletailles bigint, OUT nbarticletaillel bigint, OUT nbarticletaillexl bigint, OUT nbarticletaillem bigint, OUT nbarticletaillexxl bigint, OUT nbarticletailleunique bigint) OWNER TO "JpA";

--
-- TOC entry 327 (class 1255 OID 51165)
-- Name: matrice_2(character varying, character varying); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.matrice_2(character varying, character varying, OUT numero integer, OUT modelearticle character varying, OUT colorisarticle character varying, OUT nbarticletotal bigint, OUT nbarticletailles bigint, OUT nbarticletaillel bigint, OUT nbarticletaillexl bigint, OUT nbarticletaillem bigint, OUT nbarticletaillexxl bigint, OUT nbarticletailleunique bigint) RETURNS SETOF record
    LANGUAGE sql
    AS $_$  

SELECT a.numeromodele AS numero, a.modelearticle, a.colorisarticle, ( SELECT sum(COALESCE(commandeligne__tbl.nbarticletailles, 0) + COALESCE(commandeligne__tbl.nbarticletaillel, 0) + COALESCE(commandeligne__tbl.nbarticletaillem, 0) + COALESCE(commandeligne__tbl.nbarticletaillexl, 0) + COALESCE(commandeligne__tbl.nbarticletaillexxl, 0)) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletotal, ( SELECT COALESCE(sum(commandeligne__tbl.nbarticletailles), 0::bigint) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletailles, ( SELECT sum(commandeligne__tbl.nbarticletaillel) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletaillel, ( SELECT sum(commandeligne__tbl.nbarticletaillexl) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletaillexl, ( SELECT sum(commandeligne__tbl.nbarticletaillem) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletaillem, ( SELECT sum(commandeligne__tbl.nbarticletaillexxl) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletaillexxl, ( SELECT sum(commandeligne__tbl.nbarticletailleunique) AS sum
           FROM commande__tbl
      JOIN commandeligne__tbl ON commande__tbl.codecommande = commandeligne__tbl.codecommande
     WHERE commandeligne__tbl.codearticle = a.codearticle AND commande__tbl.saisoncommande = a.saisonarticle AND (commande__tbl.codeetat = 1 OR commande__tbl.codeetat = 2)) AS nbarticletailleunique
   FROM article__tbl a
  WHERE a.saisonarticle = $1
  AND a.codematricefabrication = $2
  ORDER BY a.numeromodele, a.modelearticle; 

$_$;


ALTER FUNCTION public.matrice_2(character varying, character varying, OUT numero integer, OUT modelearticle character varying, OUT colorisarticle character varying, OUT nbarticletotal bigint, OUT nbarticletailles bigint, OUT nbarticletaillel bigint, OUT nbarticletaillexl bigint, OUT nbarticletaillem bigint, OUT nbarticletaillexxl bigint, OUT nbarticletailleunique bigint) OWNER TO "JpA";

--
-- TOC entry 328 (class 1255 OID 51337)
-- Name: matrice_3(character varying, character varying); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.matrice_3(character varying, character varying, OUT numero integer, OUT modelearticle character varying, OUT colorisarticle character varying, OUT nbarticletotal bigint, OUT nbarticletailles bigint, OUT nbarticletaillel bigint, OUT nbarticletaillexl bigint, OUT nbarticletaillem bigint, OUT nbarticletaillexxl bigint, OUT nbarticletailleunique bigint) RETURNS SETOF record
    LANGUAGE sql
    AS $_$
  

SELECT a.numeromodele AS numero, a.modelearticle, a.colorisarticle, 
SUM(COALESCE(CL.nbarticletailleunique, 0)) + SUM(COALESCE(CL.nbarticletailles, 0)) + SUM(COALESCE(CL.nbarticletaillem, 0)) + SUM(COALESCE(CL.nbarticletaillel, 0)) + SUM(COALESCE(CL.nbarticletaillexl, 0)) + SUM(COALESCE(CL.nbarticletaillexxl, 0)) AS nbarticletotal, 
SUM(COALESCE(CL.nbarticletailleunique, 0)) AS nbarticletailleunique,
SUM(COALESCE(CL.nbarticletailles, 0)) AS nbarticletailles, 
SUM(COALESCE(CL.nbarticletaillel, 0)) AS nbarticletaillel, 
SUM(COALESCE(CL.nbarticletaillem, 0)) AS nbarticletaillem, 
SUM(COALESCE(CL.nbarticletaillexl, 0)) AS nbarticletaillexl, 
SUM(COALESCE(CL.nbarticletaillexxl, 0)) AS nbarticletaillexxl
FROM article__tbl A, commande__tbl C, commandeligne__tbl CL 
WHERE C.codecommande = CL.codecommande
AND (C.codeetat = 1 OR C.codeetat = 2)
AND a.codearticle = CL.codearticle
AND a.saisonarticle = $1
AND a.codematricefabrication = $2
GROUP BY a.numeromodele, a.modelearticle, a.colorisarticle
ORDER BY a.numeromodele, a.modelearticle; 

$_$;


ALTER FUNCTION public.matrice_3(character varying, character varying, OUT numero integer, OUT modelearticle character varying, OUT colorisarticle character varying, OUT nbarticletotal bigint, OUT nbarticletailles bigint, OUT nbarticletaillel bigint, OUT nbarticletaillexl bigint, OUT nbarticletaillem bigint, OUT nbarticletaillexxl bigint, OUT nbarticletailleunique bigint) OWNER TO "JpA";

--
-- TOC entry 329 (class 1255 OID 51419)
-- Name: matricelingedemaison(character varying, character varying); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.matricelingedemaison(character varying, character varying, OUT numero integer, OUT modelearticle character varying, OUT colorisarticle character varying, OUT imagearticle bytea, OUT nbarticletotal bigint, OUT nbarticletailleunique bigint) RETURNS SETOF record
    LANGUAGE sql
    AS $_$
  

SELECT a.numeromodele AS numero, a.modelearticle, a.colorisarticle, a.imagearticle, 
SUM(COALESCE(CL.nbarticletailleunique, 0)) AS nbarticletotal, 
SUM(COALESCE(CL.nbarticletailleunique, 0)) AS nbarticletailleunique
FROM article__tbl A, commande__tbl C, commandeligne__tbl CL 
WHERE C.codecommande = CL.codecommande
AND (C.codeetat = 1 OR C.codeetat = 2)
AND a.codearticle = CL.codearticle
AND a.saisonarticle = $1
AND a.codematricefabrication = $2
GROUP BY a.numeromodele, a.modelearticle, a.colorisarticle, a.imagearticle
ORDER BY a.numeromodele, a.modelearticle; 

$_$;


ALTER FUNCTION public.matricelingedemaison(character varying, character varying, OUT numero integer, OUT modelearticle character varying, OUT colorisarticle character varying, OUT imagearticle bytea, OUT nbarticletotal bigint, OUT nbarticletailleunique bigint) OWNER TO "JpA";

--
-- TOC entry 321 (class 1255 OID 25453)
-- Name: montant_tva_escompte_par_commande(character varying, numeric); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.montant_tva_escompte_par_commande(character varying, numeric) RETURNS SETOF public.montant_tva_escompte_par_commande
    LANGUAGE plpgsql
    AS $_$

-- retourne une ensemble de ligne 
DECLARE
 montant montant_tva_escompte_par_commande;
BEGIN
FOR  montant IN SELECT ( SUM(montanttotaltva) * ($2 / 100) ) 
		FROM commandeligne__tbl
		WHERE codecommande = $1 
		GROUP BY tauxtvaarticle
     LOOP
         -- cette syntaxe est nécessaire pour retourner plusieurs lignes
         RETURN NEXT  montant;
     END  LOOP;
RETURN ;
END ;
 $_$;


ALTER FUNCTION public.montant_tva_escompte_par_commande(character varying, numeric) OWNER TO "JpA";

--
-- TOC entry 322 (class 1255 OID 25454)
-- Name: montant_tva_escompte_par_facture(character varying, numeric); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.montant_tva_escompte_par_facture(character varying, numeric) RETURNS SETOF public.montant_tva_escompte_par_facture
    LANGUAGE plpgsql
    AS $_$

-- retourne une ensemble de ligne 
DECLARE
 montant montant_tva_escompte_par_facture;
BEGIN
FOR  montant IN SELECT ( SUM(montanttotaltva) * ($2 / 100) ) 
		FROM factureligne__tbl
		WHERE codefacture = $1 
		GROUP BY tauxtvaarticle
     LOOP
         -- cette syntaxe est nécessaire pour retourner plusieurs lignes
         RETURN NEXT  montant;
     END  LOOP;
RETURN ;
END ;
 $_$;


ALTER FUNCTION public.montant_tva_escompte_par_facture(character varying, numeric) OWNER TO "JpA";

--
-- TOC entry 323 (class 1255 OID 25455)
-- Name: montant_tva_escompte_par_facture(character varying, numeric, numeric); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.montant_tva_escompte_par_facture(character varying, numeric, numeric) RETURNS SETOF public.montant_tva_escompte_par_facture
    LANGUAGE plpgsql
    AS $_$

-- retourne une ensemble de ligne  
DECLARE
  montant montant_tva_escompte_par_facture;
  _strcodefacture character varying (50);
  _pourcentageescompte numeric(10,2);
  _pourcentageremise numeric(10,2);

BEGIN
_strcodefacture = $1;
_pourcentageescompte = $2;
_pourcentageremise = $3;

FOR  montant IN SELECT ( SUM( (montanttotalht - (montanttotalht * (_pourcentageremise /100))) * (tauxtvaarticle / 100)) * (_pourcentageescompte / 100) ) 
		FROM factureligne__tbl
		WHERE codefacture = _strcodefacture 
		GROUP BY tauxtvaarticle
     LOOP
         -- cette syntaxe est nécessaire pour retourner plusieurs lignes
         RETURN NEXT  montant;
     END  LOOP;
RETURN ;
END ;
 $_$;


ALTER FUNCTION public.montant_tva_escompte_par_facture(character varying, numeric, numeric) OWNER TO "JpA";

--
-- TOC entry 324 (class 1255 OID 25456)
-- Name: montant_tva_par_facture(character varying, numeric); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.montant_tva_par_facture(character varying, numeric) RETURNS SETOF public.montant_tva_par_facture
    LANGUAGE plpgsql
    AS $_$

-- retourne une ensemble de ligne 
DECLARE
  montant montant_tva_par_facture;
  _strcodefacture character varying (50);
  _pourcentageremise numeric(10,2);

BEGIN
_strcodefacture = $1;
_pourcentageremise = $2;

FOR  montant IN SELECT ( SUM( (montanttotalht - (montanttotalht * (_pourcentageremise /100))) * (tauxtvaarticle / 100) ) ) 
		FROM factureligne__tbl
		WHERE codefacture = _strcodefacture 
		GROUP BY tauxtvaarticle
     LOOP
         -- cette syntaxe est nécessaire pour retourner plusieurs lignes
         RETURN NEXT  montant;
     END  LOOP;
RETURN ;
END ;
 $_$;


ALTER FUNCTION public.montant_tva_par_facture(character varying, numeric) OWNER TO "JpA";

--
-- TOC entry 325 (class 1255 OID 25457)
-- Name: relevedecomptesavecreport(character varying, character varying); Type: FUNCTION; Schema: public; Owner: JpA
--

CREATE FUNCTION public.relevedecomptesavecreport(__codetiers character varying, __saison character varying) RETURNS TABLE(raisonsocialetiers character varying, adresse1tiers character varying, codepostaltiers character varying, villetiers character varying, codetiers character varying, saisoncommande character varying, montant numeric, dateecheance date, montantreglementclient numeric, datereglementclient date, libelle character varying, delta numeric, codefacture character varying, devise character varying)
    LANGUAGE plpgsql
    AS $$

DECLARE
	_debitAReporter	 numeric(12,5);
	_creditAReporter numeric(12,5);
	_soldeAReporter numeric(12,5);

BEGIN
	DROP TABLE IF EXISTS tmpReleveDeComptes;
	CREATE TEMPORARY TABLE tmpReleveDeComptes (  
						   _raisonsocialetiers character varying(50),
						   _adresse1tiers character varying(100),
						   _codepostaltiers character varying(50),
						   _villetiers character varying(50),
						   _codetiers character varying(50),
						   _saisoncommande character varying(50),
						   _montant numeric(12,5),
						   _dateecheance date,
  						   _montantreglementclient numeric(12,5),
						   _datereglementclient date,
						   _libelle character varying(255),
  						   _delta numeric(12,5),
						   _codefacture character varying(50),
						   _devise character varying(5)
						) 
	ON commit DROP ;

	SELECT SUM((F.montanttotalttcfacture + F.montantacompteverse)) INTO _debitAReporter 
	FROM facture__tbl F, Tiers__tbl T 
	WHERE F.codetiers = T.codetiers AND F.codeetat <> 3
	AND F.codetiers = __codetiers 
	AND F.saisonfacture IN (SELECT saison FROM saison__tbl WHERE saisondatedebut < (SELECT saisondatedebut FROM saison__tbl WHERE saison = __saison));


	SELECT SUM(R.montantreglementclient) INTO _creditAReporter 
        FROM ReglementClient__tbl R, Tiers__tbl T 
        WHERE R.codetiers = T.codetiers 
        AND R.codetiers = __codetiers 
        AND R.saison  IN (SELECT saison FROM saison__tbl WHERE saisondatedebut < (SELECT saisondatedebut FROM saison__tbl WHERE saison = __saison));

	_soldeAReporter = _debitAReporter - _creditAReporter;

	IF ( _soldeAReporter > POW(10, -2) ) THEN
		IF ( _soldeAReporter < 0.0 ) THEN
			INSERT INTO tmpReleveDeComptes (  _raisonsocialetiers, _adresse1tiers, _codepostaltiers, _villetiers, _codetiers, _saisoncommande, _montant, _dateecheance, 
							  _montantreglementclient, _datereglementclient, _libelle, _delta, _codefacture, _devise ) 
			SELECT T.raisonsocialetiers, T.adresse1tiers, T.codepostaltiers, T.villetiers,  T.codetiers, __saison, NULL, 
			NULL, _soldeAReporter, NULL, 'Report', _soldeAReporter, NULL, T.devise 
			FROM Tiers__tbl T 
			WHERE T.codetiers = __codetiers;
		END IF;
		IF ( _soldeAReporter > 0.0 ) THEN
			INSERT INTO tmpReleveDeComptes (  _raisonsocialetiers, _adresse1tiers, _codepostaltiers, _villetiers, _codetiers, _saisoncommande, _montant, _dateecheance, 
							  _montantreglementclient, _datereglementclient, _libelle, _delta, _codefacture, _devise ) 
			SELECT T.raisonsocialetiers, T.adresse1tiers, T.codepostaltiers, T.villetiers,  T.codetiers, __saison, _soldeAReporter, 
			NULL, NULL, NULL, 'Report', -_soldeAReporter, NULL, T.devise 
			FROM Tiers__tbl T 
			WHERE T.codetiers = __codetiers;
		END IF; 
	END IF;

	

	INSERT INTO tmpReleveDeComptes (  _raisonsocialetiers, _adresse1tiers, _codepostaltiers, _villetiers, _codetiers, _saisoncommande, _montant, _dateecheance, 
					  _montantreglementclient, _datereglementclient, _libelle, _delta, _codefacture, _devise ) 
	SELECT T.raisonsocialetiers, T.adresse1tiers, T.codepostaltiers, T.villetiers,  F.codetiers, F.saisonfacture, (F.montanttotalttcfacture + F.montantacompteverse), 
	       F.datefacture, NULL, NULL, F.libellefacture, -(F.montanttotalttcfacture + F.montantacompteverse), F.codefacture, T.devise 
	       FROM facture__tbl F, Tiers__tbl T 
	       WHERE F.codetiers = T.codetiers AND F.codeetat <> 3
	       AND F.codetiers = __codetiers AND F.saisonfacture = __saison
	       ORDER BY F.codefacture;

	INSERT INTO tmpReleveDeComptes (  _raisonsocialetiers, _adresse1tiers, _codepostaltiers, _villetiers, _codetiers, _saisoncommande, _montant, _dateecheance, 
					  _montantreglementclient, _datereglementclient, _libelle, _delta, _codefacture, _devise ) 
        SELECT T.raisonsocialetiers, T.adresse1tiers, T.codepostaltiers, T.villetiers, R.codetiers, R.saison, NULL, NULL, 
               R.montantreglementclient, R.dateecheancereglement, R.libellereglementclient,
               R.montantreglementclient, NULL, T.devise 
               FROM ReglementClient__tbl R, Tiers__tbl T 
               WHERE R.codetiers = T.codetiers 
               AND R.codetiers = __codetiers AND R.saison = __saison
	       ORDER BY R.dateecheancereglement;

	      
    RETURN QUERY SELECT * FROM tmpReleveDeComptes;
	
END ;
$$;


ALTER FUNCTION public.relevedecomptesavecreport(__codetiers character varying, __saison character varying) OWNER TO "JpA";

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 233 (class 1259 OID 25458)
-- Name: agenda__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.agenda__tbl (
    compteuragenda integer NOT NULL,
    codeutilisateur character varying(50),
    debutagenda timestamp without time zone,
    finagenda timestamp without time zone,
    objetagenda character varying(150),
    lieuagenda character varying(100),
    couleuragenda character varying(6),
    detailagenda text,
    typerdvagenda integer,
    datecreationagenda date,
    datedernieremodificationagenda date
);


ALTER TABLE public.agenda__tbl OWNER TO "JpA";

--
-- TOC entry 234 (class 1259 OID 25463)
-- Name: article__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.article__tbl (
    codearticle character varying(25) NOT NULL,
    modelearticle character varying(50),
    designationarticle character varying(250),
    saisonarticle character varying(50),
    colorisarticle character varying(50),
    prixdeventehtarticle numeric(10,2),
    tauxtvaarticle numeric(10,2),
    prixdeventettcarticle numeric(10,2),
    imagearticle bytea,
    datecreationarticle date,
    datedernieremodificationarticle date,
    poidsarticle numeric(10,3) DEFAULT 0.000,
    numeroataarticle integer DEFAULT 0,
    numeromodele integer,
    codematricefabrication character varying(25)
);


ALTER TABLE public.article__tbl OWNER TO "JpA";

--
-- TOC entry 235 (class 1259 OID 25470)
-- Name: autorisationacces__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.autorisationacces__tbl (
    codepersonnel character varying(50),
    compteurmenu integer NOT NULL,
    autorise boolean
);


ALTER TABLE public.autorisationacces__tbl OWNER TO "JpA";

--
-- TOC entry 236 (class 1259 OID 25473)
-- Name: banque__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.banque__tbl (
    codebanque character varying(15) NOT NULL,
    compteursociete integer NOT NULL,
    nombanque character varying(50),
    adresse1banque character varying(50),
    adresse2banque character varying(50),
    codepostalbanque character varying(10),
    villebanque character varying(50),
    paysbanque character varying(50),
    codeagence character varying(5),
    codeguichet character varying(5),
    numerocompte character varying(11),
    cle character varying(2),
    iban character varying(50),
    bic character varying(15)
);


ALTER TABLE public.banque__tbl OWNER TO "JpA";

--
-- TOC entry 237 (class 1259 OID 25476)
-- Name: carnetata__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.carnetata__tbl (
    codecarnetata character varying(6) NOT NULL,
    saisoncarnetata character varying(50),
    designationcarnetata character varying(255),
    datecreationcarnetata date,
    datedernieremodificationcarnetata date
);


ALTER TABLE public.carnetata__tbl OWNER TO "JpA";

--
-- TOC entry 238 (class 1259 OID 25479)
-- Name: carnetataligne__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.carnetataligne__tbl (
    compteurcarnetataligne integer DEFAULT nextval(('public.seq_compteurcarnetataligne'::text)::regclass) NOT NULL,
    codecarnetata character varying(6),
    modele character varying(50),
    coloris character varying(50),
    prixdeventehtarticle numeric(10,2),
    poidsarticle numeric(10,3) DEFAULT 0.000,
    quantite integer,
    origine character varying(10)
);


ALTER TABLE public.carnetataligne__tbl OWNER TO "JpA";

--
-- TOC entry 239 (class 1259 OID 25484)
-- Name: codefils__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.codefils__tbl (
    codepere character varying(20),
    libellecodefils character varying(150)
);


ALTER TABLE public.codefils__tbl OWNER TO "JpA";

--
-- TOC entry 240 (class 1259 OID 25487)
-- Name: codepere__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.codepere__tbl (
    codepere character varying(20) NOT NULL,
    libellecodepere character varying(100)
);


ALTER TABLE public.codepere__tbl OWNER TO "JpA";

--
-- TOC entry 241 (class 1259 OID 25490)
-- Name: coloris__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.coloris__tbl (
    saison character varying(50) NOT NULL,
    coloris character varying(50) NOT NULL,
    datecreationcoloris date,
    datedernieremodificationcoloris date,
    ordredetrifabrication integer,
    composition character varying(250),
    compositionenanglais character varying(250),
    lieudefabrication character varying(150)
);


ALTER TABLE public.coloris__tbl OWNER TO "JpA";

--
-- TOC entry 242 (class 1259 OID 25495)
-- Name: commande__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.commande__tbl (
    codecommande character varying(50) NOT NULL,
    libellecommande character varying(255),
    saisoncommande character varying(50),
    datecommande date,
    codetiers character varying(50) NOT NULL,
    raisonsocialetiers character varying(50),
    adresse1tiers character varying(100),
    adresse2tiers character varying(100),
    codepostaltiers character varying(50),
    villetiers character varying(50),
    paystiers character varying(50),
    telephone1tiers character varying(20),
    telephone2tiers character varying(20),
    telephone3tiers character varying(20),
    telecopietiers character varying(20),
    adressedemessagerietiers character varying(50),
    numerosirettiers character varying(20),
    nbarticlecommande integer,
    montanttotalhtcommande numeric(10,5),
    montanttotaltvacommande numeric(10,5),
    montanttotalttccommande numeric(10,5),
    codeetat integer,
    periodedelivraison character varying(100),
    dateenvoibon date,
    dateenvoilettredeconfirmation date,
    realisation character varying(150),
    datecreationcommande date,
    datedernieremodificationcommande date,
    montantmodalitedereglement numeric(10,5) DEFAULT 0,
    fraisdeportht numeric(10,5) DEFAULT 0,
    montantnetfinancierhtcommande numeric(10,5) DEFAULT 0,
    tauxtvafraisdeport numeric(10,2) DEFAULT 0,
    montanttvafraisdeport numeric(10,5) DEFAULT 0,
    dateecheanceenvoicheque date,
    montantbruthtcommande numeric(10,5) DEFAULT 0,
    montantbruttvacommande numeric(10,5) DEFAULT 0,
    exonerationtva boolean DEFAULT false,
    codebanque character varying(15),
    montantreglement numeric(10,5) DEFAULT 0,
    tauxtaxeparafiscale numeric(10,2) DEFAULT 0,
    montanttaxeparafiscale numeric(10,2) DEFAULT 0,
    montanttvataxeparafiscale numeric(10,2) DEFAULT 0,
    tauxtvataxeparafiscale numeric(10,2) DEFAULT 19.6,
    languedecorrespondance character varying(15),
    pourcentageescompteenvisage numeric(10,2) DEFAULT 0,
    montantescompteenvisage numeric(10,5) DEFAULT 0,
    montanttvaescompteenvisage numeric(10,5) DEFAULT 0,
    montanttotalhtcommandeenvisage numeric(10,5) DEFAULT 0,
    montanttotaltvacommandeenvisage numeric(10,5) DEFAULT 0,
    montanttotalttccommandeenvisage numeric(10,5) DEFAULT 0,
    pourcentageescompte numeric(10,2) DEFAULT 0,
    montantescompte numeric(10,5) DEFAULT 0,
    montanttvaescompte numeric(10,5) DEFAULT 0,
    devisecommande character varying(5),
    montantacompteverse numeric(10,5) DEFAULT 0,
    exonerationtpf boolean
);


ALTER TABLE public.commande__tbl OWNER TO "JpA";

--
-- TOC entry 243 (class 1259 OID 25523)
-- Name: etatobjet__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.etatobjet__tbl (
    groupe character varying(20) NOT NULL,
    codeetat smallint NOT NULL,
    numeroaffichage smallint,
    libelleetat character varying(30),
    commentaire character varying(255)
);


ALTER TABLE public.etatobjet__tbl OWNER TO "JpA";

--
-- TOC entry 244 (class 1259 OID 25526)
-- Name: commandeetatobjet; Type: VIEW; Schema: public; Owner: JpA
--

CREATE VIEW public.commandeetatobjet AS
 SELECT c.codecommande,
    c.libellecommande,
    c.saisoncommande,
    c.datecommande,
    c.codetiers,
    c.raisonsocialetiers,
    c.adresse1tiers,
    c.adresse2tiers,
    c.codepostaltiers,
    c.villetiers,
    c.paystiers,
    c.telephone1tiers,
    c.telephone2tiers,
    c.telephone3tiers,
    c.telecopietiers,
    c.adressedemessagerietiers,
    c.numerosirettiers,
    c.nbarticlecommande,
    c.montanttotalhtcommande,
    c.montanttotaltvacommande,
    c.montanttotalttccommande,
    c.codeetat,
    c.periodedelivraison,
    c.dateenvoibon,
    c.dateenvoilettredeconfirmation,
    c.realisation,
    c.datecreationcommande,
    c.datedernieremodificationcommande,
    c.montantmodalitedereglement,
    c.fraisdeportht,
    c.montantnetfinancierhtcommande,
    c.tauxtvafraisdeport,
    c.montanttvafraisdeport,
    c.dateecheanceenvoicheque,
    c.montantbruthtcommande,
    c.montantbruttvacommande,
    c.exonerationtva,
    c.codebanque,
    c.montantreglement,
    c.tauxtaxeparafiscale,
    c.montanttaxeparafiscale,
    c.montanttvataxeparafiscale,
    c.tauxtvataxeparafiscale,
    c.languedecorrespondance,
    c.pourcentageescompteenvisage,
    c.montantescompteenvisage,
    c.montanttvaescompteenvisage,
    c.montanttotalhtcommandeenvisage,
    c.montanttotaltvacommandeenvisage,
    c.montanttotalttccommandeenvisage,
    c.pourcentageescompte,
    c.montantescompte,
    c.montanttvaescompte,
    c.devisecommande,
    c.montantacompteverse,
    c.exonerationtpf,
    eo.libelleetat
   FROM (public.commande__tbl c
     JOIN public.etatobjet__tbl eo ON ((c.codeetat = eo.codeetat)))
  WHERE ((eo.groupe)::text = 'COMMANDECLIENT'::text);


ALTER VIEW public.commandeetatobjet OWNER TO "JpA";

--
-- TOC entry 245 (class 1259 OID 25531)
-- Name: commandeligne__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.commandeligne__tbl (
    compteurcommandeligne integer DEFAULT nextval(('public.seq_compteurcommandeligne'::text)::regclass) NOT NULL,
    codecommande character varying(50) NOT NULL,
    codearticle character varying(25) NOT NULL,
    modelearticle character varying(50),
    designationarticle character varying(250),
    colorisarticle character varying(50),
    prixdeventehtarticle numeric(10,2),
    tauxtvaarticle numeric(10,2),
    prixdeventettcarticle numeric(10,5),
    nbarticletailles integer,
    nbarticletaillem integer,
    nbarticletaillel integer,
    nbarticle integer,
    montanttotalht numeric(10,5),
    montanttotaltva numeric(10,5),
    montanttotalttc numeric(10,5),
    datecreationcommandeligne date,
    datedernieremodificationcommandeligne date,
    nbarticletailleslivre integer DEFAULT 0,
    nbarticletaillemlivre integer DEFAULT 0,
    nbarticletaillellivre integer DEFAULT 0,
    nbarticlelivre integer DEFAULT 0,
    nbarticletaillexl integer DEFAULT 0,
    nbarticletaillexllivre integer DEFAULT 0,
    nbarticletailleunique integer DEFAULT 0,
    nbarticletailleuniquelivre integer DEFAULT 0,
    nbarticletaillexxl integer DEFAULT 0,
    nbarticletaillexxllivre integer DEFAULT 0
);


ALTER TABLE public.commandeligne__tbl OWNER TO "JpA";

--
-- TOC entry 246 (class 1259 OID 25545)
-- Name: commandemodalitedereglement__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.commandemodalitedereglement__tbl (
    compteurcommandemodalitedereglement integer DEFAULT nextval(('public.seq_compteurcommandemodalitedereglement'::text)::regclass) NOT NULL,
    codecommande character varying(50) NOT NULL,
    modedereglement character varying(50),
    nbjoursdedecalage integer,
    pourcentage double precision,
    montant numeric(10,5),
    dateecheance date,
    datecreationcommandemodalitedereglement date,
    datedernieremodificationcommandemodalitedereglement date
);


ALTER TABLE public.commandemodalitedereglement__tbl OWNER TO "JpA";

--
-- TOC entry 247 (class 1259 OID 25549)
-- Name: modele__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.modele__tbl (
    saison character varying(50) NOT NULL,
    modele character varying(50) NOT NULL,
    datecreationmodele date,
    datedernieremodificationmodele date,
    tailleunique boolean DEFAULT false,
    type character varying(50),
    typeenanglais character varying(50),
    numero integer
);


ALTER TABLE public.modele__tbl OWNER TO "JpA";

--
-- TOC entry 248 (class 1259 OID 25553)
-- Name: fabrication; Type: VIEW; Schema: public; Owner: JpA
--

CREATE VIEW public.fabrication AS
 SELECT ( SELECT modele__tbl.numero
           FROM public.modele__tbl
          WHERE (((modele__tbl.modele)::text = (a.modelearticle)::text) AND ((modele__tbl.saison)::text = (a.saisonarticle)::text))) AS numero,
    a.modelearticle,
    a.colorisarticle,
    ( SELECT sum((((COALESCE(commandeligne__tbl.nbarticletailles, 0) + COALESCE(commandeligne__tbl.nbarticletaillel, 0)) + COALESCE(commandeligne__tbl.nbarticletaillem, 0)) + COALESCE(commandeligne__tbl.nbarticletaillexl, 0))) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletotal,
    ( SELECT COALESCE(sum(commandeligne__tbl.nbarticletailles), (0)::bigint) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletailles,
    ( SELECT sum(commandeligne__tbl.nbarticletaillel) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletaillel,
    ( SELECT sum(commandeligne__tbl.nbarticletaillexl) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletaillexl,
    ( SELECT sum(commandeligne__tbl.nbarticletaillem) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletaillem,
    0 AS nbarticletailleunique
   FROM public.article__tbl a
  WHERE ((a.saisonarticle)::text = 'PRINTEMPS-ETE 2013'::text)
  ORDER BY ( SELECT modele__tbl.numero
           FROM public.modele__tbl
          WHERE (((modele__tbl.modele)::text = (a.modelearticle)::text) AND ((modele__tbl.saison)::text = (a.saisonarticle)::text))), a.modelearticle;


ALTER VIEW public.fabrication OWNER TO "JpA";

--
-- TOC entry 249 (class 1259 OID 25558)
-- Name: facture__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.facture__tbl (
    codefacture character varying(50) NOT NULL,
    codecommande character varying(50),
    typefacture character varying(25),
    libellefacture character varying(255),
    saisonfacture character varying(50),
    datefacture date,
    codetiers character varying(50) NOT NULL,
    raisonsocialetiers character varying(50),
    adresse1tiers character varying(100),
    adresse2tiers character varying(100),
    codepostaltiers character varying(50),
    villetiers character varying(50),
    paystiers character varying(50),
    telephone1tiers character varying(20),
    telephone2tiers character varying(20),
    telephone3tiers character varying(20),
    telecopietiers character varying(20),
    adressedemessagerietiers character varying(50),
    numerosirettiers character varying(20),
    nbarticlefacture integer,
    montanttotalhtfacture numeric(10,5),
    montanttotaltvafacture numeric(10,5),
    montanttotalttcfacture numeric(10,5),
    codeetat integer,
    periodedefacture character varying(100),
    dateenvoibon date,
    dateenvoilettredeconfirmation date,
    realisation character varying(150),
    montantmodalitedereglement numeric(10,5) DEFAULT 0,
    fraisdeportttc numeric(10,5) DEFAULT 0,
    fraisdeportht numeric(10,5) DEFAULT 0,
    montantnetfinancierhtfacture numeric(10,5) DEFAULT 0,
    tauxtvafraisdeport numeric(10,2) DEFAULT 0,
    montanttvafraisdeport numeric(10,5) DEFAULT 0,
    dateecheanceenvoicheque date,
    montantbruthtfacture numeric(10,5) DEFAULT 0,
    montantbruttvafacture numeric(10,5) DEFAULT 0,
    exonerationtva boolean DEFAULT false,
    codebanque character varying(15),
    montantreglement numeric(10,5) DEFAULT 0,
    datecreationfacture date,
    datedernieremodificationfacture date,
    codelivraison character varying(50),
    tauxtaxeparafiscale numeric(10,2) DEFAULT 0,
    montanttaxeparafiscale numeric(10,2) DEFAULT 0,
    montanttvataxeparafiscale numeric(10,2) DEFAULT 0,
    tauxtvataxeparafiscale numeric(10,2) DEFAULT 19.6,
    pourcentageescompte numeric(10,2) DEFAULT 0,
    montantescompte numeric(10,5) DEFAULT 0,
    montanttvaescompte numeric(10,5) DEFAULT 0,
    montantacompteverse numeric(10,5) DEFAULT 0,
    devisefacture character varying(5),
    languedecorrespondance character varying(15),
    interlocuteur character varying(50),
    avoirsurarticle boolean DEFAULT false,
    origine character varying(15) DEFAULT 'C.E.E.'::character varying,
    pourcentageremise numeric(10,2) DEFAULT 0,
    montantremise numeric(10,5) DEFAULT 0,
    netcommercialht numeric(10,5) DEFAULT 0,
    exonerationtpf boolean,
    shippinginsurance numeric(10,5) DEFAULT 0,
    montantacompteverseht numeric(10,5),
    tauxtvaacompteverse numeric(10,5),
    montanttvaacompteverse numeric(10,5),
    codefactureacompte character varying(50)
);


ALTER TABLE public.facture__tbl OWNER TO "JpA";

--
-- TOC entry 250 (class 1259 OID 25587)
-- Name: factureetatobjet; Type: VIEW; Schema: public; Owner: JpA
--

CREATE VIEW public.factureetatobjet AS
 SELECT f.codefacture,
    f.codecommande,
    f.typefacture,
    f.libellefacture,
    f.saisonfacture,
    f.datefacture,
    f.codetiers,
    f.raisonsocialetiers,
    f.adresse1tiers,
    f.adresse2tiers,
    f.codepostaltiers,
    f.villetiers,
    f.paystiers,
    f.telephone1tiers,
    f.telephone2tiers,
    f.telephone3tiers,
    f.telecopietiers,
    f.adressedemessagerietiers,
    f.numerosirettiers,
    f.nbarticlefacture,
    f.montanttotalhtfacture,
    f.montanttotaltvafacture,
    f.montanttotalttcfacture,
    f.codeetat,
    f.periodedefacture,
    f.dateenvoibon,
    f.dateenvoilettredeconfirmation,
    f.realisation,
    f.montantmodalitedereglement,
    f.fraisdeportttc,
    f.fraisdeportht,
    f.montantnetfinancierhtfacture,
    f.tauxtvafraisdeport,
    f.montanttvafraisdeport,
    f.dateecheanceenvoicheque,
    f.montantbruthtfacture,
    f.montantbruttvafacture,
    f.exonerationtva,
    f.codebanque,
    f.montantreglement,
    f.datecreationfacture,
    f.datedernieremodificationfacture,
    f.codelivraison,
    f.tauxtaxeparafiscale,
    f.montanttaxeparafiscale,
    f.montanttvataxeparafiscale,
    f.tauxtvataxeparafiscale,
    f.pourcentageescompte,
    f.montantescompte,
    f.montanttvaescompte,
    f.montantacompteverse,
    f.devisefacture,
    f.languedecorrespondance,
    f.interlocuteur,
    f.avoirsurarticle,
    f.origine,
    f.pourcentageremise,
    f.montantremise,
    f.netcommercialht,
    f.exonerationtpf,
    f.shippinginsurance,
    eo.libelleetat
   FROM (public.facture__tbl f
     JOIN public.etatobjet__tbl eo ON ((f.codeetat = eo.codeetat)))
  WHERE ((eo.groupe)::text = 'FACTURECLIENT'::text);


ALTER VIEW public.factureetatobjet OWNER TO "JpA";

--
-- TOC entry 251 (class 1259 OID 25592)
-- Name: factureligne__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.factureligne__tbl (
    compteurfactureligne integer DEFAULT nextval(('public.seq_compteurfactureligne'::text)::regclass) NOT NULL,
    codefacture character varying(50) NOT NULL,
    codecommande character varying(50),
    compteurcommandeligne integer,
    codearticle character varying(25),
    modelearticle character varying(50),
    designationarticle character varying(250),
    colorisarticle character varying(50),
    prixdeventehtarticle numeric(10,2),
    tauxtvaarticle numeric(10,2),
    prixdeventettcarticle numeric(10,5),
    nbarticletailles integer,
    nbarticletaillem integer,
    nbarticletaillel integer,
    nbarticle integer,
    nbarticletaillesencommande integer,
    nbarticletaillemencommande integer,
    nbarticletaillelencommande integer,
    nbarticleencommande integer,
    montanttotalht numeric(10,5),
    montanttotaltva numeric(10,5),
    montanttotalttc numeric(10,5),
    datecreationfactureligne date,
    datedernieremodificationfactureligne date,
    nbarticletaillexl integer DEFAULT 0,
    nbarticletaillexlencommande integer DEFAULT 0,
    codelivraison character varying(50),
    compteurlivraisonligne integer,
    typefacture character varying(25),
    nbarticletailleunique integer DEFAULT 0,
    nbarticletailleuniqueencommande integer DEFAULT 0,
    nbarticletaillexxl integer DEFAULT 0,
    nbarticletaillexxlencommande integer DEFAULT 0,
    prixdeventefobarticle numeric(10,2)
);


ALTER TABLE public.factureligne__tbl OWNER TO "JpA";

--
-- TOC entry 252 (class 1259 OID 25604)
-- Name: formproperties__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.formproperties__tbl (
    codeutilisateur character varying(50),
    nomformulaire character varying(250),
    nomcomposant character varying(250),
    ordre integer,
    largeur integer,
    nomchamp character varying(250),
    nomchampenclair character varying(250),
    ordredetri integer,
    sensdetri character varying(25),
    atrier integer,
    alignementcolonne integer
);


ALTER TABLE public.formproperties__tbl OWNER TO "JpA";

--
-- TOC entry 253 (class 1259 OID 25609)
-- Name: livraison__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.livraison__tbl (
    codelivraison character varying(50) NOT NULL,
    codecommande character varying(50),
    libellelivraison character varying(255),
    saisonlivraison character varying(50),
    datelivraison date,
    codetiers character varying(50) NOT NULL,
    raisonsocialetiers character varying(50),
    adresse1tiers character varying(100),
    adresse2tiers character varying(100),
    codepostaltiers character varying(50),
    villetiers character varying(50),
    paystiers character varying(50),
    telephone1tiers character varying(20),
    telephone2tiers character varying(20),
    telephone3tiers character varying(20),
    telecopietiers character varying(20),
    adressedemessagerietiers character varying(50),
    numerosirettiers character varying(20),
    nbarticlelivraison integer,
    montanttotalhtlivraison numeric(10,5),
    montanttotaltvalivraison numeric(10,5),
    montanttotalttclivraison numeric(10,5),
    codeetat integer,
    periodedelivraison character varying(100),
    dateenvoibon date,
    dateenvoilettredeconfirmation date,
    realisation character varying(150),
    montantmodalitedereglement numeric(10,5) DEFAULT 0,
    fraisdeportttc numeric(10,5) DEFAULT 0,
    fraisdeportht numeric(10,5) DEFAULT 0,
    montantnetfinancierhtlivraison numeric(10,5) DEFAULT 0,
    tauxtvafraisdeport numeric(10,2) DEFAULT 0,
    montanttvafraisdeport numeric(10,5) DEFAULT 0,
    dateecheanceenvoicheque date,
    montantbruthtlivraison numeric(10,5) DEFAULT 0,
    montantbruttvalivraison numeric(10,5) DEFAULT 0,
    exonerationtva boolean DEFAULT false,
    codebanque character varying(15),
    montantreglement numeric(10,5) DEFAULT 0,
    datecreationlivraison date,
    datedernieremodificationlivraison date,
    tauxtaxeparafiscale numeric(10,2) DEFAULT 0,
    montanttaxeparafiscale numeric(10,2) DEFAULT 0,
    montanttvataxeparafiscale numeric(10,2) DEFAULT 0,
    tauxtvataxeparafiscale numeric(10,2) DEFAULT 19.6,
    languedecorrespondance character varying(15) DEFAULT 'FRANCAIS'::character varying,
    deviselivraison character varying(5) DEFAULT 'EUR'::character varying,
    interlocuteur character varying(50),
    exonerationtpf boolean
);


ALTER TABLE public.livraison__tbl OWNER TO "JpA";

--
-- TOC entry 254 (class 1259 OID 25630)
-- Name: livraisonetatobjet; Type: VIEW; Schema: public; Owner: JpA
--

CREATE VIEW public.livraisonetatobjet AS
 SELECT l.codelivraison,
    l.codecommande,
    l.libellelivraison,
    l.saisonlivraison,
    l.datelivraison,
    l.codetiers,
    l.raisonsocialetiers,
    l.adresse1tiers,
    l.adresse2tiers,
    l.codepostaltiers,
    l.villetiers,
    l.paystiers,
    l.telephone1tiers,
    l.telephone2tiers,
    l.telephone3tiers,
    l.telecopietiers,
    l.adressedemessagerietiers,
    l.numerosirettiers,
    l.nbarticlelivraison,
    l.montanttotalhtlivraison,
    l.montanttotaltvalivraison,
    l.montanttotalttclivraison,
    l.codeetat,
    l.periodedelivraison,
    l.dateenvoibon,
    l.dateenvoilettredeconfirmation,
    l.realisation,
    l.montantmodalitedereglement,
    l.fraisdeportttc,
    l.fraisdeportht,
    l.montantnetfinancierhtlivraison,
    l.tauxtvafraisdeport,
    l.montanttvafraisdeport,
    l.dateecheanceenvoicheque,
    l.montantbruthtlivraison,
    l.montantbruttvalivraison,
    l.exonerationtva,
    l.codebanque,
    l.montantreglement,
    l.datecreationlivraison,
    l.datedernieremodificationlivraison,
    l.tauxtaxeparafiscale,
    l.montanttaxeparafiscale,
    l.montanttvataxeparafiscale,
    l.tauxtvataxeparafiscale,
    l.languedecorrespondance,
    l.deviselivraison,
    l.interlocuteur,
    l.exonerationtpf,
    eo.libelleetat
   FROM (public.livraison__tbl l
     JOIN public.etatobjet__tbl eo ON ((l.codeetat = eo.codeetat)))
  WHERE ((eo.groupe)::text = 'LIVRAISONCLIENT'::text);


ALTER VIEW public.livraisonetatobjet OWNER TO "JpA";

--
-- TOC entry 255 (class 1259 OID 25635)
-- Name: livraisonligne__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.livraisonligne__tbl (
    compteurlivraisonligne integer DEFAULT nextval(('public.seq_compteurlivraisonligne'::text)::regclass) NOT NULL,
    codelivraison character varying(50) NOT NULL,
    codecommande character varying(50),
    compteurcommandeligne integer,
    codearticle character varying(25) NOT NULL,
    modelearticle character varying(50),
    designationarticle character varying(250),
    colorisarticle character varying(50),
    prixdeventehtarticle numeric(10,2),
    tauxtvaarticle numeric(10,2),
    prixdeventettcarticle numeric(10,5),
    nbarticletailles integer,
    nbarticletaillem integer,
    nbarticletaillel integer,
    nbarticle integer,
    nbarticletaillesencommande integer,
    nbarticletaillemencommande integer,
    nbarticletaillelencommande integer,
    nbarticleencommande integer,
    montanttotalht numeric(10,5),
    montanttotaltva numeric(10,5),
    montanttotalttc numeric(10,5),
    datecreationlivraisonligne date,
    datedernieremodificationlivraisonligne date,
    nbarticletaillexl integer DEFAULT 0,
    nbarticletaillessoldelivraison integer,
    nbarticletaillemsoldelivraison integer,
    nbarticletaillelsoldelivraison integer,
    nbarticletaillexlsoldelivraison integer,
    nbarticlesoldelivraison integer,
    nbarticletaillexlencommande integer,
    nbarticletailleunique integer DEFAULT 0,
    nbarticletaillexxl integer DEFAULT 0,
    nbarticletailleuniquesoldelivraison integer DEFAULT 0,
    nbarticletaillexxlsoldelivraison integer DEFAULT 0,
    nbarticletailleuniqueencommande integer DEFAULT 0,
    nbarticletaillexxlencommande integer DEFAULT 0
);


ALTER TABLE public.livraisonligne__tbl OWNER TO "JpA";

--
-- TOC entry 256 (class 1259 OID 25648)
-- Name: matrice__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.matrice__tbl (
    saison character varying(50) NOT NULL,
    ordreprincipal integer,
    numeromodele integer,
    coloris character varying(50) NOT NULL,
    ordresecondaire integer,
    modele character varying(50)
);


ALTER TABLE public.matrice__tbl OWNER TO "JpA";

--
-- TOC entry 257 (class 1259 OID 25651)
-- Name: menuapp__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.menuapp__tbl (
    compteurmenu integer NOT NULL,
    compteurmenupere integer,
    id_fonction integer,
    sousgroupe character varying(255),
    description character varying(255),
    rang smallint,
    menupardefaut boolean
);


ALTER TABLE public.menuapp__tbl OWNER TO "JpA";

--
-- TOC entry 258 (class 1259 OID 25656)
-- Name: newfabrication; Type: VIEW; Schema: public; Owner: JpA
--

CREATE VIEW public.newfabrication AS
 SELECT ( SELECT modele__tbl.numero
           FROM public.modele__tbl
          WHERE (((modele__tbl.modele)::text = (a.modelearticle)::text) AND ((modele__tbl.saison)::text = (a.saisonarticle)::text))) AS numero,
    a.modelearticle,
    a.colorisarticle,
    ( SELECT sum(((((COALESCE(commandeligne__tbl.nbarticletailles, 0) + COALESCE(commandeligne__tbl.nbarticletaillel, 0)) + COALESCE(commandeligne__tbl.nbarticletaillem, 0)) + COALESCE(commandeligne__tbl.nbarticletaillexl, 0)) + COALESCE(commandeligne__tbl.nbarticletaillexxl, 0))) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletotal,
    ( SELECT COALESCE(sum(commandeligne__tbl.nbarticletailles), (0)::bigint) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletailles,
    ( SELECT sum(commandeligne__tbl.nbarticletaillel) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletaillel,
    ( SELECT sum(commandeligne__tbl.nbarticletaillexl) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletaillexl,
    ( SELECT sum(commandeligne__tbl.nbarticletaillem) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletaillem,
    ( SELECT sum(commandeligne__tbl.nbarticletaillexxl) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletaillexxl,
    ( SELECT sum(commandeligne__tbl.nbarticletailleunique) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (a.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (a.saisonarticle)::text) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletailleunique
   FROM public.article__tbl a
  WHERE ((a.saisonarticle)::text = 'PRINTEMPS ETE 2014'::text)
  ORDER BY ( SELECT modele__tbl.numero
           FROM public.modele__tbl
          WHERE (((modele__tbl.modele)::text = (a.modelearticle)::text) AND ((modele__tbl.saison)::text = (a.saisonarticle)::text))), a.modelearticle;


ALTER VIEW public.newfabrication OWNER TO "JpA";

--
-- TOC entry 259 (class 1259 OID 25661)
-- Name: parametre__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.parametre__tbl (
    compteurparametre integer NOT NULL,
    afficherhorloge boolean DEFAULT false,
    afficherrdv boolean DEFAULT false,
    derniercodetiers smallint DEFAULT 1,
    derniercodearticle smallint DEFAULT 0,
    prochaincodecommande smallint DEFAULT 1,
    tauxtvadefaut numeric(10,2) DEFAULT 19.6,
    datedernieresauvegarde date,
    cheminsauvegardepardefaut character varying(255),
    cheminpg_dump character varying(255),
    saison character varying(150),
    controlesauvegarde boolean DEFAULT false,
    delaientredeuxsauvegarde smallint DEFAULT 15,
    prochaincodelivraison smallint DEFAULT 1,
    prochaincodefacture smallint DEFAULT 1,
    prochaincodefactureacompte smallint DEFAULT 1,
    prochaincodeavoir smallint DEFAULT 1,
    tauxtaxeparafiscale numeric(10,2) DEFAULT 0.07,
    identificationaulancement boolean DEFAULT false,
    pieddepagecarnetata character varying(250),
    prochaincodecarnetata smallint DEFAULT 1,
    libelleexonerationtvaue character varying(250),
    libelleexonerationtvahorsue character varying(250),
    conditiongeneraledevente text,
    libelleexport character varying(250),
    libelleexportfrancais character varying(250)
);


ALTER TABLE public.parametre__tbl OWNER TO "JpA";

--
-- TOC entry 260 (class 1259 OID 25681)
-- Name: pays__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.pays__tbl (
    pays character varying(100) NOT NULL,
    unioneuropeenne boolean,
    horsunioneuropeenne boolean,
    datecreationpays date,
    datedernieremodificationpays date,
    grandexportpays boolean DEFAULT false
);


ALTER TABLE public.pays__tbl OWNER TO "JpA";

--
-- TOC entry 261 (class 1259 OID 25685)
-- Name: personnel__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.personnel__tbl (
    codepersonnel character varying(50) NOT NULL,
    nompersonnel character varying(150),
    motdepassepersonnel character varying(25),
    titrepersonnel character varying(50),
    telephonepersonnel character varying(50),
    portablepersonnel character varying(50),
    adressedemessageriepersonnel character varying(50),
    concretpersonnel boolean DEFAULT true,
    adresse1personnel character varying(100),
    adresse2personnel character varying(100),
    codepostalpersonnel character varying(50),
    villepersonnel character varying(50),
    datecreationpersonnel date,
    datedernieremodificationpersonnel date
);


ALTER TABLE public.personnel__tbl OWNER TO "JpA";

--
-- TOC entry 262 (class 1259 OID 25691)
-- Name: reglementclient__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.reglementclient__tbl (
    compteurreglementclient integer DEFAULT nextval(('public.seq_compteurreglementclient'::text)::regclass) NOT NULL,
    codetiers character varying(50) NOT NULL,
    codecommande character varying(50),
    typedereglementclient character varying(25),
    modedereglementclient character varying(50),
    libellereglementclient character varying(50),
    montantreglementclient numeric(10,2),
    datereglementclient date,
    datecreationreglementclient date,
    datedernieremodificationreglementclient date,
    dateecheancereglement date,
    encaissereglement boolean,
    saison character varying(50),
    commentairereglementclient character varying(150)
);


ALTER TABLE public.reglementclient__tbl OWNER TO "JpA";

--
-- TOC entry 263 (class 1259 OID 25695)
-- Name: saison__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.saison__tbl (
    saison character varying(50) NOT NULL,
    datecreationsaison date,
    datedernieremodificationsaison date,
    saisondatedebut date
);


ALTER TABLE public.saison__tbl OWNER TO "JpA";

--
-- TOC entry 264 (class 1259 OID 25698)
-- Name: seq_compteurcarnetataligne; Type: SEQUENCE; Schema: public; Owner: JpA
--

CREATE SEQUENCE public.seq_compteurcarnetataligne
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_compteurcarnetataligne OWNER TO "JpA";

--
-- TOC entry 265 (class 1259 OID 25699)
-- Name: seq_compteurcommandeligne; Type: SEQUENCE; Schema: public; Owner: JpA
--

CREATE SEQUENCE public.seq_compteurcommandeligne
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_compteurcommandeligne OWNER TO "JpA";

--
-- TOC entry 266 (class 1259 OID 25700)
-- Name: seq_compteurcommandemodalitedereglement; Type: SEQUENCE; Schema: public; Owner: JpA
--

CREATE SEQUENCE public.seq_compteurcommandemodalitedereglement
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_compteurcommandemodalitedereglement OWNER TO "JpA";

--
-- TOC entry 267 (class 1259 OID 25701)
-- Name: seq_compteurfactureligne; Type: SEQUENCE; Schema: public; Owner: JpA
--

CREATE SEQUENCE public.seq_compteurfactureligne
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_compteurfactureligne OWNER TO "JpA";

--
-- TOC entry 268 (class 1259 OID 25702)
-- Name: seq_compteurlivraisonligne; Type: SEQUENCE; Schema: public; Owner: JpA
--

CREATE SEQUENCE public.seq_compteurlivraisonligne
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_compteurlivraisonligne OWNER TO "JpA";

--
-- TOC entry 269 (class 1259 OID 25703)
-- Name: seq_compteurreglementclient; Type: SEQUENCE; Schema: public; Owner: JpA
--

CREATE SEQUENCE public.seq_compteurreglementclient
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_compteurreglementclient OWNER TO "JpA";

--
-- TOC entry 270 (class 1259 OID 25704)
-- Name: seq_compteurtauxchangeusd; Type: SEQUENCE; Schema: public; Owner: JpA
--

CREATE SEQUENCE public.seq_compteurtauxchangeusd
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_compteurtauxchangeusd OWNER TO "JpA";

--
-- TOC entry 271 (class 1259 OID 25705)
-- Name: seq_compteurtiersautreadresse; Type: SEQUENCE; Schema: public; Owner: JpA
--

CREATE SEQUENCE public.seq_compteurtiersautreadresse
    START WITH 609
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_compteurtiersautreadresse OWNER TO "JpA";

--
-- TOC entry 272 (class 1259 OID 25706)
-- Name: seq_compteurtierscrm; Type: SEQUENCE; Schema: public; Owner: JpA
--

CREATE SEQUENCE public.seq_compteurtierscrm
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_compteurtierscrm OWNER TO "JpA";

--
-- TOC entry 273 (class 1259 OID 25707)
-- Name: societe__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.societe__tbl (
    compteursociete integer NOT NULL,
    titresociete character varying(50),
    nomsociete character varying(50),
    adresse1societe character varying(50),
    adresse2societe character varying(50),
    codepostalsociete character varying(10),
    villesociete character varying(50),
    payssociete character varying(50),
    telephonesociete character varying(50),
    telecopiesociete character varying(50),
    numerosiretsociete character varying(50),
    codeapesociete character varying(5),
    capitalsociete character varying(50),
    logosociete character varying(50),
    numerotvaintracommunautairesociete character varying(25),
    adresse1societelivraison character varying(100),
    adresse2societelivraison character varying(1000),
    codepostalsocietelivraison character varying(10),
    villesocietelivraison character varying(100),
    payssocietelivraison character varying(50),
    adresse1correspondance character varying(50),
    adresse2correspondance character varying(50),
    codepostalcorrespondance character varying(50),
    villecorrespondance character varying(50),
    nomcorrespondance character varying(50),
    logosocietegf bytea,
    adressedemessageriesociete character varying(250),
    sitesociete character varying(250),
    eorisociete character varying(25)
);


ALTER TABLE public.societe__tbl OWNER TO "JpA";

--
-- TOC entry 274 (class 1259 OID 25712)
-- Name: statistiqueArticleCommandeAccessoire_2; Type: VIEW; Schema: public; Owner: JpA
--

CREATE VIEW public."statistiqueArticleCommandeAccessoire_2" AS
 SELECT mo.numero,
    co.ordredetrifabrication,
    m.modelearticle,
    m.colorisarticle,
    ( SELECT sum((((COALESCE(commandeligne__tbl.nbarticletailles, 0) + COALESCE(commandeligne__tbl.nbarticletaillel, 0)) + COALESCE(commandeligne__tbl.nbarticletaillem, 0)) + COALESCE(commandeligne__tbl.nbarticletaillexl, 0))) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (m.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (m.saisonarticle)::text) AND (mo.tailleunique = true) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletotal,
    0 AS nbarticletailles,
    0 AS nbarticletaillel,
    0 AS nbarticletaillexl,
    0 AS nbarticletaillem,
    ( SELECT sum((((COALESCE(commandeligne__tbl.nbarticletailles, 0) + COALESCE(commandeligne__tbl.nbarticletaillel, 0)) + COALESCE(commandeligne__tbl.nbarticletaillem, 0)) + COALESCE(commandeligne__tbl.nbarticletaillexl, 0))) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (m.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (m.saisonarticle)::text) AND (mo.tailleunique = true) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletailleunique
   FROM ((public.article__tbl m
     JOIN public.modele__tbl mo ON ((((m.modelearticle)::text = (mo.modele)::text) AND ((m.saisonarticle)::text = (mo.saison)::text))))
     JOIN public.coloris__tbl co ON ((((m.colorisarticle)::text = (co.coloris)::text) AND ((m.saisonarticle)::text = (co.saison)::text))))
  WHERE (((m.saisonarticle)::text = 'PRINTEMPS-ETE 2012'::text) AND (mo.tailleunique = true))
  ORDER BY co.ordredetrifabrication, m.modelearticle, m.colorisarticle;


ALTER VIEW public."statistiqueArticleCommandeAccessoire_2" OWNER TO "JpA";

--
-- TOC entry 275 (class 1259 OID 25717)
-- Name: statistiqueArticleCommandeAccessoires_2; Type: VIEW; Schema: public; Owner: JpA
--

CREATE VIEW public."statistiqueArticleCommandeAccessoires_2" AS
 SELECT "statistiqueArticleCommandeAccessoire_2".numero,
    "statistiqueArticleCommandeAccessoire_2".modelearticle,
    "statistiqueArticleCommandeAccessoire_2".colorisarticle,
    sum("statistiqueArticleCommandeAccessoire_2".nbarticletotal) AS nbarticletotal,
    sum("statistiqueArticleCommandeAccessoire_2".nbarticletailles) AS nbarticletailles,
    sum("statistiqueArticleCommandeAccessoire_2".nbarticletaillem) AS nbarticletaillem,
    sum("statistiqueArticleCommandeAccessoire_2".nbarticletaillel) AS nbarticletaillel,
    sum("statistiqueArticleCommandeAccessoire_2".nbarticletaillexl) AS nbarticletaillexl,
    sum("statistiqueArticleCommandeAccessoire_2".nbarticletailleunique) AS nbarticletailleunique
   FROM public."statistiqueArticleCommandeAccessoire_2"
  GROUP BY "statistiqueArticleCommandeAccessoire_2".numero, "statistiqueArticleCommandeAccessoire_2".modelearticle, "statistiqueArticleCommandeAccessoire_2".colorisarticle
  ORDER BY "statistiqueArticleCommandeAccessoire_2".numero, "statistiqueArticleCommandeAccessoire_2".modelearticle, "statistiqueArticleCommandeAccessoire_2".colorisarticle;


ALTER VIEW public."statistiqueArticleCommandeAccessoires_2" OWNER TO "JpA";

--
-- TOC entry 276 (class 1259 OID 25721)
-- Name: statistiqueArticleCommandeToutArticle_2; Type: VIEW; Schema: public; Owner: JpA
--

CREATE VIEW public."statistiqueArticleCommandeToutArticle_2" AS
 SELECT mo.numero,
    co.ordredetrifabrication,
    m.modelearticle,
    m.colorisarticle,
    ( SELECT sum((((COALESCE(commandeligne__tbl.nbarticletailles, 0) + COALESCE(commandeligne__tbl.nbarticletaillel, 0)) + COALESCE(commandeligne__tbl.nbarticletaillem, 0)) + COALESCE(commandeligne__tbl.nbarticletaillexl, 0))) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (m.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (m.saisonarticle)::text) AND (mo.tailleunique = false) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletotal,
    ( SELECT COALESCE(sum(commandeligne__tbl.nbarticletailles), (0)::bigint) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (m.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (m.saisonarticle)::text) AND (mo.tailleunique = false) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletailles,
    ( SELECT sum(commandeligne__tbl.nbarticletaillel) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (m.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (m.saisonarticle)::text) AND (mo.tailleunique = false) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletaillel,
    ( SELECT sum(commandeligne__tbl.nbarticletaillexl) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (m.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (m.saisonarticle)::text) AND (mo.tailleunique = false) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletaillexl,
    ( SELECT sum(commandeligne__tbl.nbarticletaillem) AS sum
           FROM (public.commande__tbl
             JOIN public.commandeligne__tbl ON (((commande__tbl.codecommande)::text = (commandeligne__tbl.codecommande)::text)))
          WHERE (((commandeligne__tbl.codearticle)::text = (m.codearticle)::text) AND ((commande__tbl.saisoncommande)::text = (m.saisonarticle)::text) AND (mo.tailleunique = false) AND ((commande__tbl.codeetat = 1) OR (commande__tbl.codeetat = 2)))) AS nbarticletaillem,
    0 AS nbarticletailleunique
   FROM ((public.article__tbl m
     JOIN public.modele__tbl mo ON ((((m.modelearticle)::text = (mo.modele)::text) AND ((m.saisonarticle)::text = (mo.saison)::text))))
     JOIN public.coloris__tbl co ON ((((m.colorisarticle)::text = (co.coloris)::text) AND ((m.saisonarticle)::text = (co.saison)::text))))
  WHERE (((m.saisonarticle)::text = 'PRINTEMPS-ETE 2012'::text) AND (mo.tailleunique = false))
  ORDER BY mo.numero, m.colorisarticle;


ALTER VIEW public."statistiqueArticleCommandeToutArticle_2" OWNER TO "JpA";

--
-- TOC entry 277 (class 1259 OID 25726)
-- Name: statistiqueArticleCommandeToutArticles_2; Type: VIEW; Schema: public; Owner: JpA
--

CREATE VIEW public."statistiqueArticleCommandeToutArticles_2" AS
 SELECT "statistiqueArticleCommandeToutArticle_2".numero,
    "statistiqueArticleCommandeToutArticle_2".modelearticle,
    "statistiqueArticleCommandeToutArticle_2".colorisarticle,
    sum("statistiqueArticleCommandeToutArticle_2".nbarticletotal) AS nbarticletotal,
    sum("statistiqueArticleCommandeToutArticle_2".nbarticletailles) AS nbarticletailles,
    sum("statistiqueArticleCommandeToutArticle_2".nbarticletaillem) AS nbarticletaillem,
    sum("statistiqueArticleCommandeToutArticle_2".nbarticletaillel) AS nbarticletaillel,
    sum("statistiqueArticleCommandeToutArticle_2".nbarticletaillexl) AS nbarticletaillexl,
    sum("statistiqueArticleCommandeToutArticle_2".nbarticletailleunique) AS nbarticletailleunique
   FROM public."statistiqueArticleCommandeToutArticle_2"
  GROUP BY "statistiqueArticleCommandeToutArticle_2".numero, "statistiqueArticleCommandeToutArticle_2".modelearticle, "statistiqueArticleCommandeToutArticle_2".colorisarticle
  ORDER BY "statistiqueArticleCommandeToutArticle_2".numero, "statistiqueArticleCommandeToutArticle_2".modelearticle, "statistiqueArticleCommandeToutArticle_2".colorisarticle;


ALTER VIEW public."statistiqueArticleCommandeToutArticles_2" OWNER TO "JpA";

--
-- TOC entry 278 (class 1259 OID 25730)
-- Name: tauxchangeusd__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.tauxchangeusd__tbl (
    compteurtauxchangeusd integer DEFAULT nextval(('public.seq_compteurtauxchangeusd'::text)::regclass) NOT NULL,
    datetauxchangeusd date NOT NULL,
    ratetauxchangeusd numeric(10,5)
);


ALTER TABLE public.tauxchangeusd__tbl OWNER TO "JpA";

--
-- TOC entry 279 (class 1259 OID 25734)
-- Name: tiers__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.tiers__tbl (
    typetiers character varying(50),
    codetiers character varying(50) NOT NULL,
    titretiers character varying(50),
    raisonsocialetiers character varying(50),
    adresse1tiers character varying(100),
    adresse2tiers character varying(100),
    codepostaltiers character varying(50),
    villetiers character varying(50),
    paystiers character varying(50),
    telephone1tiers character varying(20),
    telephone2tiers character varying(20),
    telephone3tiers character varying(20),
    telecopietiers character varying(20),
    adressedemessagerietiers character varying(150),
    observationtiers text,
    numerosirettiers character varying(20),
    datecreationtiers date,
    datedernieremodificationtiers date,
    devise character varying(5),
    languedecorrespondance character varying(15),
    numerotvaintracommunautairetiers character varying(25),
    exonerationtva boolean,
    interlocuteurtiers character varying(50),
    comptetiers character varying(8),
    clientexport boolean,
    exonerationtpf boolean,
    categorietiers character varying(1)
);


ALTER TABLE public.tiers__tbl OWNER TO "JpA";

--
-- TOC entry 280 (class 1259 OID 25739)
-- Name: tiersautreadresse__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.tiersautreadresse__tbl (
    compteurtiersautreadresse integer DEFAULT nextval(('public.seq_compteurtiersautreadresse'::text)::regclass) NOT NULL,
    typeadressetiersautreadresse character varying(50) NOT NULL,
    codetiers character varying(50) NOT NULL,
    raisonsocialetiersautreadresse character varying(50),
    adresse1tiersautreadresse character varying(100),
    adresse2tiersautreadresse character varying(100),
    codepostaltiersautreadresse character varying(50),
    villetiersautreadresse character varying(50),
    paystiersautreadresse character varying(50),
    datecreationtiersautreadresse date,
    datedernieremodificationtiersautreadresse date
);


ALTER TABLE public.tiersautreadresse__tbl OWNER TO "JpA";

--
-- TOC entry 281 (class 1259 OID 25745)
-- Name: tierscrm__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.tierscrm__tbl (
    compteurtierscrm integer DEFAULT nextval(('public.seq_compteurtierscrm'::text)::regclass) NOT NULL,
    codetiers character varying(50) NOT NULL,
    codepiece character varying(50),
    datetierscrm date,
    classificationtierscrm character varying(50),
    objettierscrm character varying(250),
    descriptiondetailletierscrm text,
    datecreationtierscrm date,
    datedernieremodificationtierscrm date
);


ALTER TABLE public.tierscrm__tbl OWNER TO "JpA";

--
-- TOC entry 282 (class 1259 OID 25751)
-- Name: traduction__tbl; Type: TABLE; Schema: public; Owner: JpA
--

CREATE TABLE public.traduction__tbl (
    francais character varying(255),
    anglais character varying(255)
);


ALTER TABLE public.traduction__tbl OWNER TO "JpA";

--
-- TOC entry 3554 (class 2606 OID 25757)
-- Name: agenda__tbl agenda__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.agenda__tbl
    ADD CONSTRAINT agenda__tbl_pkey PRIMARY KEY (compteuragenda);


--
-- TOC entry 3557 (class 2606 OID 25759)
-- Name: article__tbl article__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.article__tbl
    ADD CONSTRAINT article__tbl_pkey PRIMARY KEY (codearticle);


--
-- TOC entry 3559 (class 2606 OID 25761)
-- Name: banque__tbl banque__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.banque__tbl
    ADD CONSTRAINT banque__tbl_pkey PRIMARY KEY (codebanque);


--
-- TOC entry 3561 (class 2606 OID 25763)
-- Name: carnetata__tbl carnetata__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.carnetata__tbl
    ADD CONSTRAINT carnetata__tbl_pkey PRIMARY KEY (codecarnetata);


--
-- TOC entry 3563 (class 2606 OID 25765)
-- Name: carnetataligne__tbl carnetataligne__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.carnetataligne__tbl
    ADD CONSTRAINT carnetataligne__tbl_pkey PRIMARY KEY (compteurcarnetataligne);


--
-- TOC entry 3567 (class 2606 OID 25767)
-- Name: codepere__tbl codepere__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.codepere__tbl
    ADD CONSTRAINT codepere__tbl_pkey PRIMARY KEY (codepere);


--
-- TOC entry 3569 (class 2606 OID 25769)
-- Name: coloris__tbl coloris__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.coloris__tbl
    ADD CONSTRAINT coloris__tbl_pkey PRIMARY KEY (saison, coloris);


--
-- TOC entry 3573 (class 2606 OID 25771)
-- Name: commandeligne__tbl commandeligne__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.commandeligne__tbl
    ADD CONSTRAINT commandeligne__tbl_pkey PRIMARY KEY (compteurcommandeligne);


--
-- TOC entry 3575 (class 2606 OID 25773)
-- Name: commandemodalitedereglement__tbl commandemodalitedereglement__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.commandemodalitedereglement__tbl
    ADD CONSTRAINT commandemodalitedereglement__tbl_pkey PRIMARY KEY (compteurcommandemodalitedereglement);


--
-- TOC entry 3594 (class 2606 OID 25775)
-- Name: reglementclient__tbl commandereglement__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.reglementclient__tbl
    ADD CONSTRAINT commandereglement__tbl_pkey PRIMARY KEY (compteurreglementclient);


--
-- TOC entry 3571 (class 2606 OID 25777)
-- Name: etatobjet__tbl etatobjet__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.etatobjet__tbl
    ADD CONSTRAINT etatobjet__tbl_pkey PRIMARY KEY (groupe, codeetat);


--
-- TOC entry 3579 (class 2606 OID 25779)
-- Name: factureligne__tbl factureligne__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.factureligne__tbl
    ADD CONSTRAINT factureligne__tbl_pkey PRIMARY KEY (compteurfactureligne);


--
-- TOC entry 3582 (class 2606 OID 25781)
-- Name: livraisonligne__tbl livraisonligne__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.livraisonligne__tbl
    ADD CONSTRAINT livraisonligne__tbl_pkey PRIMARY KEY (compteurlivraisonligne);


--
-- TOC entry 3586 (class 2606 OID 25783)
-- Name: menuapp__tbl menuapp__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.menuapp__tbl
    ADD CONSTRAINT menuapp__tbl_pkey PRIMARY KEY (compteurmenu);


--
-- TOC entry 3577 (class 2606 OID 25785)
-- Name: modele__tbl modele__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.modele__tbl
    ADD CONSTRAINT modele__tbl_pkey PRIMARY KEY (saison, modele);


--
-- TOC entry 3588 (class 2606 OID 25787)
-- Name: parametre__tbl parametre__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.parametre__tbl
    ADD CONSTRAINT parametre__tbl_pkey PRIMARY KEY (compteurparametre);


--
-- TOC entry 3590 (class 2606 OID 25789)
-- Name: pays__tbl pays__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.pays__tbl
    ADD CONSTRAINT pays__tbl_pkey PRIMARY KEY (pays);


--
-- TOC entry 3592 (class 2606 OID 25791)
-- Name: personnel__tbl personnel__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.personnel__tbl
    ADD CONSTRAINT personnel__tbl_pkey PRIMARY KEY (codepersonnel);


--
-- TOC entry 3596 (class 2606 OID 25793)
-- Name: saison__tbl saison__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.saison__tbl
    ADD CONSTRAINT saison__tbl_pkey PRIMARY KEY (saison);


--
-- TOC entry 3598 (class 2606 OID 25795)
-- Name: societe__tbl societe__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.societe__tbl
    ADD CONSTRAINT societe__tbl_pkey PRIMARY KEY (compteursociete);


--
-- TOC entry 3600 (class 2606 OID 25797)
-- Name: tauxchangeusd__tbl tauxchangeusd__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.tauxchangeusd__tbl
    ADD CONSTRAINT tauxchangeusd__tbl_pkey PRIMARY KEY (compteurtauxchangeusd);


--
-- TOC entry 3603 (class 2606 OID 25799)
-- Name: tiers__tbl tiers__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.tiers__tbl
    ADD CONSTRAINT tiers__tbl_pkey PRIMARY KEY (codetiers);


--
-- TOC entry 3605 (class 2606 OID 25801)
-- Name: tiersautreadresse__tbl tiersautreadresse__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.tiersautreadresse__tbl
    ADD CONSTRAINT tiersautreadresse__tbl_pkey PRIMARY KEY (compteurtiersautreadresse);


--
-- TOC entry 3607 (class 2606 OID 25803)
-- Name: tierscrm__tbl tierscrm__tbl_pkey; Type: CONSTRAINT; Schema: public; Owner: JpA
--

ALTER TABLE ONLY public.tierscrm__tbl
    ADD CONSTRAINT tierscrm__tbl_pkey PRIMARY KEY (compteurtierscrm);


--
-- TOC entry 3580 (class 1259 OID 25804)
-- Name: idx1; Type: INDEX; Schema: public; Owner: JpA
--

CREATE INDEX idx1 ON public.formproperties__tbl USING btree (codeutilisateur, nomformulaire, nomcomposant, ordre);


--
-- TOC entry 3564 (class 1259 OID 25805)
-- Name: idxcodepere; Type: INDEX; Schema: public; Owner: JpA
--

CREATE INDEX idxcodepere ON public.codefils__tbl USING btree (codepere);


--
-- TOC entry 3555 (class 1259 OID 25806)
-- Name: idxcodeutilisateurdebutagenda; Type: INDEX; Schema: public; Owner: JpA
--

CREATE INDEX idxcodeutilisateurdebutagenda ON public.agenda__tbl USING btree (codeutilisateur, debutagenda);


--
-- TOC entry 3583 (class 1259 OID 25807)
-- Name: idxcompteurmenupere; Type: INDEX; Schema: public; Owner: JpA
--

CREATE INDEX idxcompteurmenupere ON public.menuapp__tbl USING btree (compteurmenupere);


--
-- TOC entry 3565 (class 1259 OID 25808)
-- Name: idxlibellecodefils; Type: INDEX; Schema: public; Owner: JpA
--

CREATE INDEX idxlibellecodefils ON public.codefils__tbl USING btree (libellecodefils);


--
-- TOC entry 3601 (class 1259 OID 25809)
-- Name: idxraisonsocialetiers; Type: INDEX; Schema: public; Owner: JpA
--

CREATE INDEX idxraisonsocialetiers ON public.tiers__tbl USING btree (raisonsocialetiers);


--
-- TOC entry 3584 (class 1259 OID 25810)
-- Name: idxrang; Type: INDEX; Schema: public; Owner: JpA
--

CREATE INDEX idxrang ON public.menuapp__tbl USING btree (rang);


--
-- TOC entry 3610 (class 2620 OID 25811)
-- Name: commande__tbl trgafterdeletecommande; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgafterdeletecommande AFTER DELETE ON public.commande__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgafterdeletecommande();


--
-- TOC entry 3615 (class 2620 OID 25812)
-- Name: facture__tbl trgafterdeletefacture; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgafterdeletefacture AFTER DELETE ON public.facture__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgafterdeletefacture();


--
-- TOC entry 3619 (class 2620 OID 25813)
-- Name: livraison__tbl trgafterdeletelivraison; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgafterdeletelivraison AFTER DELETE ON public.livraison__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgafterdeletelivraison();


--
-- TOC entry 3612 (class 2620 OID 25814)
-- Name: commandeligne__tbl trgafterinsertorupdatecommandeligne; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgafterinsertorupdatecommandeligne AFTER INSERT OR DELETE OR UPDATE ON public.commandeligne__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgafterinsertorupdateordeletecommandeligne();


--
-- TOC entry 3614 (class 2620 OID 25815)
-- Name: commandemodalitedereglement__tbl trgafterinsertorupdatecommandemodalitedereglement; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgafterinsertorupdatecommandemodalitedereglement AFTER INSERT OR DELETE OR UPDATE ON public.commandemodalitedereglement__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgafterinsertorupdateordeletecommandemodalitedereglement();


--
-- TOC entry 3617 (class 2620 OID 25816)
-- Name: factureligne__tbl trgafterinsertorupdatefactureligne; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgafterinsertorupdatefactureligne AFTER INSERT OR DELETE OR UPDATE ON public.factureligne__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgafterinsertorupdateordeletefactureligne();


--
-- TOC entry 3621 (class 2620 OID 25817)
-- Name: livraisonligne__tbl trgafterinsertorupdatelivraisonligne; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgafterinsertorupdatelivraisonligne AFTER INSERT OR DELETE OR UPDATE ON public.livraisonligne__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgafterinsertorupdateordeletelivraisonligne();


--
-- TOC entry 3624 (class 2620 OID 25818)
-- Name: reglementclient__tbl trgafterinsertorupdatereglementclient; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgafterinsertorupdatereglementclient AFTER INSERT OR DELETE OR UPDATE ON public.reglementclient__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgafterinsertorupdateordeletereglementclient();


--
-- TOC entry 3608 (class 2620 OID 25819)
-- Name: agenda__tbl trgbeforeinsertorupdateagenda; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgbeforeinsertorupdateagenda BEFORE INSERT OR UPDATE ON public.agenda__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgbeforeinsertorupdateagenda();


--
-- TOC entry 3609 (class 2620 OID 25820)
-- Name: article__tbl trgbeforeinsertorupdatearticle; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgbeforeinsertorupdatearticle BEFORE INSERT OR UPDATE ON public.article__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgbeforeinsertorupdatearticle();


--
-- TOC entry 3611 (class 2620 OID 25821)
-- Name: commande__tbl trgbeforeinsertorupdatecommande; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgbeforeinsertorupdatecommande BEFORE INSERT OR UPDATE ON public.commande__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgbeforeinsertorupdatecommande();


--
-- TOC entry 3613 (class 2620 OID 25822)
-- Name: commandeligne__tbl trgbeforeinsertorupdatecommandeligne; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgbeforeinsertorupdatecommandeligne BEFORE INSERT OR UPDATE ON public.commandeligne__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgbeforeinsertorupdatecommandeligne();


--
-- TOC entry 3616 (class 2620 OID 25823)
-- Name: facture__tbl trgbeforeinsertorupdatefacture; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgbeforeinsertorupdatefacture BEFORE INSERT OR UPDATE ON public.facture__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgbeforeinsertorupdatefacture();


--
-- TOC entry 3618 (class 2620 OID 25824)
-- Name: factureligne__tbl trgbeforeinsertorupdatefactureligne; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgbeforeinsertorupdatefactureligne BEFORE INSERT OR UPDATE ON public.factureligne__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgbeforeinsertorupdatefactureligne();


--
-- TOC entry 3620 (class 2620 OID 25825)
-- Name: livraison__tbl trgbeforeinsertorupdatelivraison; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgbeforeinsertorupdatelivraison BEFORE INSERT OR UPDATE ON public.livraison__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgbeforeinsertorupdatelivraison();


--
-- TOC entry 3622 (class 2620 OID 25826)
-- Name: livraisonligne__tbl trgbeforeinsertorupdatelivraisonligne; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgbeforeinsertorupdatelivraisonligne BEFORE INSERT OR UPDATE ON public.livraisonligne__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgbeforeinsertorupdatelivraisonligne();


--
-- TOC entry 3623 (class 2620 OID 25827)
-- Name: personnel__tbl trgbeforeinsertorupdatepersonnel; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgbeforeinsertorupdatepersonnel BEFORE INSERT OR UPDATE ON public.personnel__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgbeforeinsertorupdatepersonnel();


--
-- TOC entry 3625 (class 2620 OID 25828)
-- Name: tiers__tbl trgbeforeinsertorupdatetiers; Type: TRIGGER; Schema: public; Owner: JpA
--

CREATE TRIGGER trgbeforeinsertorupdatetiers BEFORE INSERT OR UPDATE ON public.tiers__tbl FOR EACH ROW EXECUTE FUNCTION public.ftrgbeforeinsertorupdatetiers();


--
-- TOC entry 3782 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT USAGE ON SCHEMA public TO "lesOurs_db-ro";
GRANT CREATE ON SCHEMA public TO "lesOurs_db-admin";


--
-- TOC entry 3783 (class 0 OID 0)
-- Dependencies: 233
-- Name: TABLE agenda__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.agenda__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.agenda__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.agenda__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.agenda__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3784 (class 0 OID 0)
-- Dependencies: 234
-- Name: TABLE article__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.article__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.article__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.article__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.article__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3785 (class 0 OID 0)
-- Dependencies: 235
-- Name: TABLE autorisationacces__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.autorisationacces__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.autorisationacces__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.autorisationacces__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.autorisationacces__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3786 (class 0 OID 0)
-- Dependencies: 236
-- Name: TABLE banque__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.banque__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.banque__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.banque__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.banque__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3787 (class 0 OID 0)
-- Dependencies: 237
-- Name: TABLE carnetata__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.carnetata__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.carnetata__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.carnetata__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.carnetata__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3788 (class 0 OID 0)
-- Dependencies: 238
-- Name: TABLE carnetataligne__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.carnetataligne__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.carnetataligne__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.carnetataligne__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.carnetataligne__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3789 (class 0 OID 0)
-- Dependencies: 239
-- Name: TABLE codefils__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.codefils__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.codefils__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.codefils__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.codefils__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3790 (class 0 OID 0)
-- Dependencies: 240
-- Name: TABLE codepere__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.codepere__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.codepere__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.codepere__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.codepere__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3791 (class 0 OID 0)
-- Dependencies: 241
-- Name: TABLE coloris__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.coloris__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.coloris__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.coloris__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.coloris__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3792 (class 0 OID 0)
-- Dependencies: 242
-- Name: TABLE commande__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.commande__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.commande__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.commande__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.commande__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3793 (class 0 OID 0)
-- Dependencies: 243
-- Name: TABLE etatobjet__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.etatobjet__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.etatobjet__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.etatobjet__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.etatobjet__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3794 (class 0 OID 0)
-- Dependencies: 244
-- Name: TABLE commandeetatobjet; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.commandeetatobjet TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.commandeetatobjet TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.commandeetatobjet TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.commandeetatobjet TO "lesOurs_db-overquota";


--
-- TOC entry 3795 (class 0 OID 0)
-- Dependencies: 245
-- Name: TABLE commandeligne__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.commandeligne__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.commandeligne__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.commandeligne__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.commandeligne__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3796 (class 0 OID 0)
-- Dependencies: 246
-- Name: TABLE commandemodalitedereglement__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.commandemodalitedereglement__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.commandemodalitedereglement__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.commandemodalitedereglement__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.commandemodalitedereglement__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3797 (class 0 OID 0)
-- Dependencies: 247
-- Name: TABLE modele__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.modele__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.modele__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.modele__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.modele__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3798 (class 0 OID 0)
-- Dependencies: 248
-- Name: TABLE fabrication; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.fabrication TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.fabrication TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.fabrication TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.fabrication TO "lesOurs_db-overquota";


--
-- TOC entry 3799 (class 0 OID 0)
-- Dependencies: 249
-- Name: TABLE facture__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.facture__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.facture__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.facture__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.facture__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3800 (class 0 OID 0)
-- Dependencies: 250
-- Name: TABLE factureetatobjet; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.factureetatobjet TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.factureetatobjet TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.factureetatobjet TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.factureetatobjet TO "lesOurs_db-overquota";


--
-- TOC entry 3801 (class 0 OID 0)
-- Dependencies: 251
-- Name: TABLE factureligne__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.factureligne__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.factureligne__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.factureligne__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.factureligne__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3802 (class 0 OID 0)
-- Dependencies: 252
-- Name: TABLE formproperties__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.formproperties__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.formproperties__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.formproperties__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.formproperties__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3803 (class 0 OID 0)
-- Dependencies: 253
-- Name: TABLE livraison__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.livraison__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.livraison__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.livraison__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.livraison__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3804 (class 0 OID 0)
-- Dependencies: 254
-- Name: TABLE livraisonetatobjet; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.livraisonetatobjet TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.livraisonetatobjet TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.livraisonetatobjet TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.livraisonetatobjet TO "lesOurs_db-overquota";


--
-- TOC entry 3805 (class 0 OID 0)
-- Dependencies: 255
-- Name: TABLE livraisonligne__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.livraisonligne__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.livraisonligne__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.livraisonligne__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.livraisonligne__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3806 (class 0 OID 0)
-- Dependencies: 256
-- Name: TABLE matrice__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.matrice__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.matrice__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.matrice__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.matrice__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3807 (class 0 OID 0)
-- Dependencies: 257
-- Name: TABLE menuapp__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.menuapp__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.menuapp__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.menuapp__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.menuapp__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3808 (class 0 OID 0)
-- Dependencies: 258
-- Name: TABLE newfabrication; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.newfabrication TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.newfabrication TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.newfabrication TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.newfabrication TO "lesOurs_db-overquota";


--
-- TOC entry 3809 (class 0 OID 0)
-- Dependencies: 259
-- Name: TABLE parametre__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.parametre__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.parametre__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.parametre__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.parametre__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3810 (class 0 OID 0)
-- Dependencies: 260
-- Name: TABLE pays__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.pays__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.pays__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.pays__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.pays__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3811 (class 0 OID 0)
-- Dependencies: 261
-- Name: TABLE personnel__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.personnel__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.personnel__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.personnel__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.personnel__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3812 (class 0 OID 0)
-- Dependencies: 262
-- Name: TABLE reglementclient__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.reglementclient__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.reglementclient__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.reglementclient__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.reglementclient__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3813 (class 0 OID 0)
-- Dependencies: 263
-- Name: TABLE saison__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.saison__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.saison__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.saison__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.saison__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3814 (class 0 OID 0)
-- Dependencies: 264
-- Name: SEQUENCE seq_compteurcarnetataligne; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON SEQUENCE public.seq_compteurcarnetataligne TO "lesOurs_db-ro";
GRANT USAGE,UPDATE ON SEQUENCE public.seq_compteurcarnetataligne TO "lesOurs_db-rw";


--
-- TOC entry 3815 (class 0 OID 0)
-- Dependencies: 265
-- Name: SEQUENCE seq_compteurcommandeligne; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON SEQUENCE public.seq_compteurcommandeligne TO "lesOurs_db-ro";
GRANT USAGE,UPDATE ON SEQUENCE public.seq_compteurcommandeligne TO "lesOurs_db-rw";


--
-- TOC entry 3816 (class 0 OID 0)
-- Dependencies: 266
-- Name: SEQUENCE seq_compteurcommandemodalitedereglement; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON SEQUENCE public.seq_compteurcommandemodalitedereglement TO "lesOurs_db-ro";
GRANT USAGE,UPDATE ON SEQUENCE public.seq_compteurcommandemodalitedereglement TO "lesOurs_db-rw";


--
-- TOC entry 3817 (class 0 OID 0)
-- Dependencies: 267
-- Name: SEQUENCE seq_compteurfactureligne; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON SEQUENCE public.seq_compteurfactureligne TO "lesOurs_db-ro";
GRANT USAGE,UPDATE ON SEQUENCE public.seq_compteurfactureligne TO "lesOurs_db-rw";


--
-- TOC entry 3818 (class 0 OID 0)
-- Dependencies: 268
-- Name: SEQUENCE seq_compteurlivraisonligne; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON SEQUENCE public.seq_compteurlivraisonligne TO "lesOurs_db-ro";
GRANT USAGE,UPDATE ON SEQUENCE public.seq_compteurlivraisonligne TO "lesOurs_db-rw";


--
-- TOC entry 3819 (class 0 OID 0)
-- Dependencies: 269
-- Name: SEQUENCE seq_compteurreglementclient; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON SEQUENCE public.seq_compteurreglementclient TO "lesOurs_db-ro";
GRANT USAGE,UPDATE ON SEQUENCE public.seq_compteurreglementclient TO "lesOurs_db-rw";


--
-- TOC entry 3820 (class 0 OID 0)
-- Dependencies: 270
-- Name: SEQUENCE seq_compteurtauxchangeusd; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON SEQUENCE public.seq_compteurtauxchangeusd TO "lesOurs_db-ro";
GRANT USAGE,UPDATE ON SEQUENCE public.seq_compteurtauxchangeusd TO "lesOurs_db-rw";


--
-- TOC entry 3821 (class 0 OID 0)
-- Dependencies: 271
-- Name: SEQUENCE seq_compteurtiersautreadresse; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON SEQUENCE public.seq_compteurtiersautreadresse TO "lesOurs_db-ro";
GRANT USAGE,UPDATE ON SEQUENCE public.seq_compteurtiersautreadresse TO "lesOurs_db-rw";


--
-- TOC entry 3822 (class 0 OID 0)
-- Dependencies: 272
-- Name: SEQUENCE seq_compteurtierscrm; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON SEQUENCE public.seq_compteurtierscrm TO "lesOurs_db-ro";
GRANT USAGE,UPDATE ON SEQUENCE public.seq_compteurtierscrm TO "lesOurs_db-rw";


--
-- TOC entry 3823 (class 0 OID 0)
-- Dependencies: 273
-- Name: TABLE societe__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.societe__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.societe__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.societe__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.societe__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3824 (class 0 OID 0)
-- Dependencies: 274
-- Name: TABLE "statistiqueArticleCommandeAccessoire_2"; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public."statistiqueArticleCommandeAccessoire_2" TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public."statistiqueArticleCommandeAccessoire_2" TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public."statistiqueArticleCommandeAccessoire_2" TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public."statistiqueArticleCommandeAccessoire_2" TO "lesOurs_db-overquota";


--
-- TOC entry 3825 (class 0 OID 0)
-- Dependencies: 275
-- Name: TABLE "statistiqueArticleCommandeAccessoires_2"; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public."statistiqueArticleCommandeAccessoires_2" TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public."statistiqueArticleCommandeAccessoires_2" TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public."statistiqueArticleCommandeAccessoires_2" TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public."statistiqueArticleCommandeAccessoires_2" TO "lesOurs_db-overquota";


--
-- TOC entry 3826 (class 0 OID 0)
-- Dependencies: 276
-- Name: TABLE "statistiqueArticleCommandeToutArticle_2"; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public."statistiqueArticleCommandeToutArticle_2" TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public."statistiqueArticleCommandeToutArticle_2" TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public."statistiqueArticleCommandeToutArticle_2" TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public."statistiqueArticleCommandeToutArticle_2" TO "lesOurs_db-overquota";


--
-- TOC entry 3827 (class 0 OID 0)
-- Dependencies: 277
-- Name: TABLE "statistiqueArticleCommandeToutArticles_2"; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public."statistiqueArticleCommandeToutArticles_2" TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public."statistiqueArticleCommandeToutArticles_2" TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public."statistiqueArticleCommandeToutArticles_2" TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public."statistiqueArticleCommandeToutArticles_2" TO "lesOurs_db-overquota";


--
-- TOC entry 3828 (class 0 OID 0)
-- Dependencies: 278
-- Name: TABLE tauxchangeusd__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.tauxchangeusd__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.tauxchangeusd__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.tauxchangeusd__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.tauxchangeusd__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3829 (class 0 OID 0)
-- Dependencies: 279
-- Name: TABLE tiers__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.tiers__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.tiers__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.tiers__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.tiers__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3830 (class 0 OID 0)
-- Dependencies: 280
-- Name: TABLE tiersautreadresse__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.tiersautreadresse__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.tiersautreadresse__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.tiersautreadresse__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.tiersautreadresse__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3831 (class 0 OID 0)
-- Dependencies: 281
-- Name: TABLE tierscrm__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.tierscrm__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.tierscrm__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.tierscrm__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.tierscrm__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 3832 (class 0 OID 0)
-- Dependencies: 282
-- Name: TABLE traduction__tbl; Type: ACL; Schema: public; Owner: JpA
--

GRANT SELECT ON TABLE public.traduction__tbl TO "lesOurs_db-ro";
GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLE public.traduction__tbl TO "lesOurs_db-rw";
GRANT TRIGGER,TRUNCATE ON TABLE public.traduction__tbl TO "lesOurs_db-admin";
GRANT DELETE ON TABLE public.traduction__tbl TO "lesOurs_db-overquota";


--
-- TOC entry 2267 (class 826 OID 25412)
-- Name: DEFAULT PRIVILEGES FOR SEQUENCES; Type: DEFAULT ACL; Schema: -; Owner: lesOurs_db-admin
--

ALTER DEFAULT PRIVILEGES FOR ROLE "lesOurs_db-admin" GRANT SELECT ON SEQUENCES TO "lesOurs_db-ro";
ALTER DEFAULT PRIVILEGES FOR ROLE "lesOurs_db-admin" GRANT USAGE,UPDATE ON SEQUENCES TO "lesOurs_db-rw";


--
-- TOC entry 2269 (class 826 OID 25414)
-- Name: DEFAULT PRIVILEGES FOR SEQUENCES; Type: DEFAULT ACL; Schema: -; Owner: JpA
--

ALTER DEFAULT PRIVILEGES FOR ROLE "JpA" GRANT SELECT ON SEQUENCES TO "lesOurs_db-ro";
ALTER DEFAULT PRIVILEGES FOR ROLE "JpA" GRANT USAGE,UPDATE ON SEQUENCES TO "lesOurs_db-rw";


--
-- TOC entry 2268 (class 826 OID 25411)
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: -; Owner: lesOurs_db-admin
--

ALTER DEFAULT PRIVILEGES FOR ROLE "lesOurs_db-admin" GRANT SELECT ON TABLES TO "lesOurs_db-ro";
ALTER DEFAULT PRIVILEGES FOR ROLE "lesOurs_db-admin" GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLES TO "lesOurs_db-rw";
ALTER DEFAULT PRIVILEGES FOR ROLE "lesOurs_db-admin" GRANT DELETE ON TABLES TO "lesOurs_db-overquota";


--
-- TOC entry 2270 (class 826 OID 25413)
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: -; Owner: JpA
--

ALTER DEFAULT PRIVILEGES FOR ROLE "JpA" GRANT SELECT ON TABLES TO "lesOurs_db-ro";
ALTER DEFAULT PRIVILEGES FOR ROLE "JpA" GRANT INSERT,REFERENCES,DELETE,UPDATE ON TABLES TO "lesOurs_db-rw";
ALTER DEFAULT PRIVILEGES FOR ROLE "JpA" GRANT TRIGGER,TRUNCATE ON TABLES TO "lesOurs_db-admin";
ALTER DEFAULT PRIVILEGES FOR ROLE "JpA" GRANT DELETE ON TABLES TO "lesOurs_db-overquota";


-- Completed on 2025-03-20 09:36:21

--
-- PostgreSQL database dump complete
--

