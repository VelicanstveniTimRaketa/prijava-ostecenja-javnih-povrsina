import { Button, Row, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { Prijava, User } from "../utils/types";
import { useEffect, useState } from "react";
import CustomList from "./CustomList";
import AlertBanner from "./AlertBanner";
import ReportList from "./ReportList";

function MyOffice() {
  const [usersInOffice, setUsersInOffice] = useState<User[]>();
  const [usersRequesting, setUsersRequesting] = useState<User[]>();
  const [dovrsenePrijave, setDovrsenePrijave] = useState<Prijava[]>();
  const [nedovrsenePrijave, setNedovrsenePrijave] = useState<Prijava[]>();

  useEffect(() => {
    //getNeaktivniGradskiUredi().then(res => setUsersRequesting(res.data));
  }, []);

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
      { value: <Button>Prihvati</Button> },
      { value: <Button>Izbriši</Button> },
    ]
  }));

  return (
    <Content style={{ display: "flex", margin: "2em" }}>
      <Row style={{ justifyContent: "space-around", flex: 1 }}>
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", flex: 1 }}>
          <Typography.Title level={2}>Korisnici u uredu</Typography.Title>
          {usersInOfficeItems && <CustomList data={usersInOfficeItems} />}
          <Typography.Title level={2}>Korisnici koji žele ući u ured</Typography.Title>
          {usersRequestingItems && usersRequestingItems.length !== 0 ? <CustomList data={usersRequestingItems} /> : <AlertBanner message="Nema dostupnih gradskih ureda" />}
        </div>
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", flex: 2 }}>
          <Typography.Title level={2}>Nedovršene Prijave</Typography.Title>
          {nedovrsenePrijave && nedovrsenePrijave.length !== 0 ? <ReportList data={nedovrsenePrijave} /> : <AlertBanner message="Nema nedovršenih prijava" />}
          <Typography.Title level={2}>Dovršene Prijave</Typography.Title>
          {dovrsenePrijave && dovrsenePrijave.length !== 0 ? <ReportList data={dovrsenePrijave} /> : <AlertBanner message="Nema dovršenih prijava" />}
        </div>
      </Row>
    </Content>
  );
}

export default MyOffice;