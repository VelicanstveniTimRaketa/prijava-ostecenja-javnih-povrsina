import { useContext, useEffect, useState } from "react";
import { StateContext } from "../utils/state";
import { getPrijave } from "../utils/fetch";
import { Prijava } from "../utils/types";
import { Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import ReportList from "../components/ReportList";
import MapJsApi from "../components/MapJsApi";

function UserReports() {
  const [data, setData] = useState<Prijava[]>();
  const [selectedPrijava, setSelectedPrijava] = useState<Prijava>();
  const { global: { user } } = useContext(StateContext);
  if (!user) throw TypeError();

  const selectedPrijavaSpot = selectedPrijava && {
    lat: selectedPrijava.lokacija.latitude,
    lng: selectedPrijava.lokacija.longitude
  };

  useEffect(() => {
    getPrijave({ kreatorId: user.id.toString() }).then(res => setData(res.data));
  }, [user.id]);

  return (
    <Content style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
      <Typography.Title level={3}>Moje prijave</Typography.Title>
      {data &&
        <div style={{ display: "flex" }}>
          <ReportList data={data} onClick={setSelectedPrijava} />
          <MapJsApi
            style={{ display: "flex", position: "sticky", top: "30vh", margin: "1em 2em" }}
            marker={selectedPrijavaSpot}
            center={selectedPrijavaSpot}
            zoom={selectedPrijava && 15}
            secondaryMarkers={selectedPrijava ? undefined : data.map(p => ({ lat: p.lokacija.latitude, lng: p.lokacija.longitude }))}
          />
        </div>
      }
    </Content>
  );
}

export default UserReports;