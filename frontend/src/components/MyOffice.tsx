import { useCallback, useContext, useEffect, useState } from "react";
import { Button, Checkbox, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { Prijava, User } from "../utils/types";
import { dovrsiPrijavu, getNepotvrdeniKorisniciUreda, getPrijave, getUred, odbijZahtjevUUred, potvrdiZahtjevUUred } from "../utils/fetch";
import { StateContext } from "../utils/state";
import { useNavigate } from "react-router-dom";
import CustomList from "./CustomList";
import AlertBanner from "./AlertBanner";
import ReportList from "./ReportList";
import Check from "./Check";

function MyOffice() {
  const { global } = useContext(StateContext);
  const navigate = useNavigate();

  const [usersInOffice, setUsersInOffice] = useState<User[]>();
  const [usersRequesting, setUsersRequesting] = useState<User[]>();
  const [dovrsenePrijave, setDovrsenePrijave] = useState<Prijava[]>();
  const [nedovrsenePrijave, setNedovrsenePrijave] = useState<Prijava[]>();

  const getData = useCallback(() => {
    const id = global.user?.ured?.id;
    if (!id) {
      console.error("no ured id");
      return;
    }
    getUred(id).then(v => setUsersInOffice(v.data?.clanovi));
    getNepotvrdeniKorisniciUreda().then(v => setUsersRequesting(v.data));
    getPrijave({ uredId: id.toString(), active: "true" }).then(v => setNedovrsenePrijave(v.data));
    getPrijave({ uredId: id.toString(), active: "false" }).then(v => setDovrsenePrijave(v.data));
  }, [global.user?.ured?.id]);

  useEffect(() => {
    getData();
  }, [getData]);

  const usersInOfficeItems = usersInOffice?.map(user => ({
    id: user.id,
    items: [
      { title: "ID:", value: user.id, },
      { title: "Korisničko ime:", value: user.username, },
      { title: "Email", value: user.email, },
    ]
  }));

  const usersRequestingItems = usersRequesting?.map(user => ({
    id: user.id,
    items: [
      { title: "ID:", value: user.id },
      { title: "Korisničko ime:", value: user.username },
      { title: "Email", value: user.email },
      { value: <Button onClick={() => potvrdiZahtjevUUred(user.id).then(getData)}>Prihvati</Button> },
      { value: <Button onClick={() => odbijZahtjevUUred(user.id).then(getData)}>Izbriši</Button> },
    ]
  }));

  const nedovrsenePrijaveItems = nedovrsenePrijave?.map(prijava => ({
    id: prijava.id,
    items: [
      { title: "ID:", value: prijava.id },
      { title: "Naziv:", value: prijava.naziv },
      { title: "Prijavitelj:", value: prijava.kreator?.username || "Anoniman" },
      { title: "Datum prijave:", value: prijava.prvoVrijemePrijave.toLocaleDateString() },
      { title: "Otklonjeno:", value: <Checkbox className="normalCursor" checked={!!prijava.vrijemeOtklona} /> },
      { value: <Button style={{ marginLeft: "2em" }} onClick={() => navigate("/explore/" + prijava.id.toString())} type="primary">Detalji</Button> },
      { value: <Button style={{ marginLeft: "2em" }} onClick={() => dovrsiPrijavu(prijava.id).then(getData)} type="primary">Označi otklonjenom</Button> }
    ]
  }));
  if (!global.user?.ured) return <div></div>;
  const ured = global.user.ured;

  return (
    <Check if={global.user.ured_status === "active"} elseNavigateTo="/">
      <Content style={{ display: "flex", margin: "2em", flexDirection: "column", alignItems: "center" }}>
        <Typography.Title level={2}>Moj ured</Typography.Title>
        <Typography style={{ fontSize: "1.5em" }}><i>{ured.naziv}</i> za <i>{ured.tipOstecenja.naziv}</i>, ID: {ured.id}</Typography>
        <div style={{ display: "flex", justifyContent: "space-around", flex: 1, width: "100%" }}>
          <div style={{ display: "flex", flexDirection: "column", alignItems: "center", flex: 1 }}>
            <Typography.Title level={2}>Korisnici u uredu</Typography.Title>
            {usersInOfficeItems && usersInOffice ? <CustomList data={usersInOfficeItems} /> : <div>Učitavanje...</div>}
            <Typography.Title level={2}>Korisnici koji žele ući u ured</Typography.Title>
            {usersRequestingItems && usersRequestingItems.length !== 0 ? <CustomList data={usersRequestingItems} /> : <AlertBanner message="Nema korisnika koji su zatražili ulazak" />}
          </div>
          <div style={{ display: "flex", flexDirection: "column", alignItems: "center", flex: 1 }}>
            <Typography.Title level={2}>Nedovršene Prijave</Typography.Title>
            {nedovrsenePrijaveItems && nedovrsenePrijaveItems.length !== 0 ? <CustomList data={nedovrsenePrijaveItems} /> : <AlertBanner message="Nema neotklonjenih prijava" />}
            <Typography.Title level={2}>Dovršene Prijave</Typography.Title>
            {dovrsenePrijave && dovrsenePrijave.length !== 0 ? <ReportList data={dovrsenePrijave} /> : <AlertBanner message="Nema otklonjenih prijava" />}
          </div>
        </div>
      </Content>
    </Check>
  );
}

export default MyOffice;