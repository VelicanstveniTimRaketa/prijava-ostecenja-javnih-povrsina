import { useContext, useEffect, useMemo, useState } from "react";
import { Button, Divider, Row, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { useGradskiUredi } from "../hooks/useGradskiUredi";
import { StateContext } from "../utils/state";
import { getNeaktivniGradskiUredi, odbijUred, potvrdiUred } from "../utils/fetch";
import { GradskiUred } from "../utils/types";
import { Link } from "react-router-dom";
import CustomList from "../components/CustomList";
import NewGradskiUred from "../components/NewGradskiUred";
import AlertBanner from "../components/AlertBanner";

function GradskiUredi() {
  const { global } = useContext(StateContext);
  const uredi = useGradskiUredi();
  const [newUred, setNewUred] = useState(false);
  const [neaktivniUredi, setNeaktivniUredi] = useState<GradskiUred[]>();

  useEffect(() => {
    if (global.user?.role !== "ADMIN") return;
    getNeaktivniGradskiUredi().then(res => setNeaktivniUredi(res.data));
  }, [global.user?.role]);

  const urediItems = useMemo(() => uredi?.map(ured => {
    const button = <Button style={{ marginLeft: "2em" }} onClick={() => { }} type="primary">Prijavi se</Button>;
    return {
      id: ured.id,
      onClick: () => { },
      items: [
        { title: "ID:", value: ured.id },
        { title: "Naziv:", value: ured.naziv },
        { title: "Oštećenje:", value: ured.tipOstecenja?.naziv },
        ...(global.user ? [{ value: button }] : [])
      ]
    };
  }), [global.user, uredi]);

  const neaktivniUrediItems = useMemo(() => neaktivniUredi?.map(ured => {
    return {
      id: ured.id,
      onClick: () => { },
      items: [
        { title: "ID:", value: ured.id },
        { title: "Naziv:", value: ured.naziv },
        { title: "Oštećenje:", value: ured.tipOstecenja?.naziv },
        ...(global.user ? [{ value: <Button style={{ marginLeft: "2em" }} onClick={() => { 
          potvrdiUred(ured.id).then(v => void v.success && getNeaktivniGradskiUredi().then(res => setNeaktivniUredi(res.data)));
         }} type="primary">Potvrdi</Button> }] : []),
        ...(global.user ? [{ value: <Button style={{ marginLeft: "2em" }} onClick={() => { 
          odbijUred(ured.id).then(v => void v.success && getNeaktivniGradskiUredi().then(res => setNeaktivniUredi(res.data)));
         }} type="primary">Odbij</Button> }] : []),
      ]
    };
  }), [global.user, neaktivniUredi]);

  return (
    <Content style={{ display: "flex", margin: "2em" }}>
      <Row style={{ justifyContent: "space-around", flex: 1 }}>
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", flex: 2 }}>
          <Typography.Title level={2}>Lista ureda</Typography.Title>
          {urediItems && urediItems.length !== 0 ? <CustomList data={urediItems} /> : <AlertBanner message="Nema dostupnih gradskih ureda" />}
          {global.user?.role === "ADMIN" && (
            <>
              <Typography.Title level={2}>Lista neaktivnih ureda</Typography.Title>
              {neaktivniUrediItems && neaktivniUrediItems.length !== 0 ? <CustomList data={neaktivniUrediItems} /> : <AlertBanner message="Nema neaktivnih gradskih ureda" />}
            </>
          )}
        </div>
        {global.user && true &&
          <>
            <Divider type="vertical" style={{ minHeight: "100%" }} />
            <div style={{ display: "flex", flex: 1, marginTop: "8em", justifyContent: "center" }}>
              {!newUred && <Button onClick={() => setNewUred(true)} type="primary">Novi gradski ured</Button>}
              {newUred && <NewGradskiUred />}
            </div>
          </>
        }
        {global.user && false &&
          <Link to="myOffice">
            <Button>Moj ured</Button>
          </Link>
        }
      </Row>
    </Content>
  );
}

export default GradskiUredi;