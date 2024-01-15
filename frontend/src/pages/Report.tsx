import { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { getPrijava } from "../utils/fetch";
import { Prijava } from "../utils/types";
import { Button, Layout, Image, Card, Flex } from "antd";
import { locationToGoogle } from "../utils/location";
import MapJsApi from "../components/MapJsApi";
import Check from "../components/Check";

interface ReportProps {
  enableEditing?: boolean
}

function Report(props: ReportProps) {
  const location = useLocation();
  const [prijava, setPrijava] = useState<Prijava | undefined>(location.state.prijava);
  const { id } = useParams();
  const navigate = useNavigate();
  const realId = Number.parseInt(id as string);

  const isIdBad = isNaN(realId) || id === undefined;

  useEffect(() => {
    if (isIdBad || prijava) return;
    getPrijava(realId).then(val => setPrijava(val.data));
  }, [isIdBad, prijava, realId]);


  if (isIdBad || !prijava) return <div>Loading</div>;
  const marker = locationToGoogle(prijava.lokacija);
  return (
    <Check if={!isIdBad && !!prijava} elseNavigateTo="/">
      <Layout style={{ margin: "2em" }}>
        <div style={{ display: "flex", justifyContent: "space-evenly", gap: "0.4em" }}>
          <div style={{ width: "30em", fontSize: "1.25em" }}>
            <Card style={{ fontSize: "1em" }}>
              <div>
                <span style={{ fontWeight: "bold" }}>Naziv: </span>
                <span>{prijava.naziv ? prijava.naziv : "Naziv prijave ne postoji."}</span>
              </div>
              <div>
                <span style={{ fontWeight: "bold" }}>Gradski ured: </span>
                <span>{prijava.gradskiUred.naziv ? prijava.gradskiUred.naziv : "Nema odabranog gradskog ureda."}</span>
              </div>
              <div>
                <span style={{ fontWeight: "bold" }}>Opis: </span>
                <span>{prijava.opis ? prijava.opis : "Nema opisa za traženu prijavu."}</span>
              </div>
              <div>
                <span style={{ fontWeight: "bold" }}>Tip oštećenja: </span>
                <span>{prijava.gradskiUred.tipOstecenja.naziv ? prijava.gradskiUred.tipOstecenja.naziv : "Nije određen tip oštećenja."}</span>
              </div>
              <div>
                <span style={{ fontWeight: "bold" }}>Parent: </span>
                <span>{prijava.parentPrijava ? prijava.parentPrijava.id : "Ne postoji parent prijava."}</span>
              </div>
              <div style={{ fontWeight: "bold" }}>Slike:</div>
              <div style={{ display: "flex", gap: "0.2em", flexWrap: "wrap" }}>{prijava.slike.length == 0 ? "Nema priloženih slika" : (
                prijava.slike.map((slika, index) => (
                  <Image id={slika.id.toString()} width={180} height={130} src={`/api/getImage/${slika.podatak}`} alt={`Slika-${index}`} />
                ))
              )}
              </div>
            </Card>
            <div style={{ display: "flex", gap: "0.8em", margin: "0.8em 0.8em" }}>
              <Button onClick={() => navigate(-1)}>Natrag</Button>
              {props.enableEditing && <Button onClick={() => navigate(`/editReport/${prijava.id}`)}>Uredi prijavu</Button>}
              {props.enableEditing && <Button danger>Izbriši prijavu</Button>}
            </div>
          </div>
          <div>
            <MapJsApi
              zoom={16}
              center={marker}
              marker={marker}
            />
          </div>
        </div>
      </Layout>
    </Check >
  );
}

export default Report;