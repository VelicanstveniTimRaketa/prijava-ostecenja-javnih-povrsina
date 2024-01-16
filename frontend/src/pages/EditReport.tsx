import { Layout, Typography } from "antd";
import { Content } from "antd/es/layout/layout";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { getPrijava, updatePrijava } from "../utils/fetch";
import NewReportForm from "../components/NewReportForm";
import { BarebonesPrijava, Prijava, Slika } from "../utils/types";
import { RcFile } from "antd/es/upload";

function convertSlikaToRcFile(slika: Slika): RcFile {
  const url = slika.podatak; // Assuming 'podatak' represents the image URL

  // Extract the file name from the URL or use a default name
  const fileName = url.substring(url.lastIndexOf("/") + 1) || "defaultFileName";

  // Create a new File object with the URL and file name
  const file = new File([url], fileName, { type: "image/*" });

  // Convert the File object to RcFile
  const rcFile: RcFile = file as RcFile;

  return rcFile;
}

function EditReport() {
  const [prijava, setPrijava] = useState<Prijava>();
  const [images, setImages] = useState<Slika[]>();
  const { id } = useParams();
  const realId = Number.parseInt(id as string);

  useEffect(() => {
    getPrijava(realId).then(res => {
      setPrijava(res.data);
      setImages(res.data?.slike);
    });
  }, [id]);

  function onSubmit(prijava: BarebonesPrijava) {
    const rcFiles: RcFile[] = images?.map(convertSlikaToRcFile) || [];

    updatePrijava(realId, prijava, rcFiles).then(v => {
      console.log(v);
    });
  }
  
  return (
    <Layout style={{ margin: "1.5em", display: "flex", alignItems: "center" }}>
      <Content style={{
          display: "flex",
          flexDirection: "column",
          maxWidth: "100%",
          alignItems: "center",
          padding: "2em 6em"
        }}>
        <Typography.Title level={2}>Uredi podatke prijave</Typography.Title>
        <hr style={{ fontWeight:"bold", color:"black" }} />
        {prijava ? (
          <NewReportForm onSubmit={onSubmit} initialData={prijava} />
        ) : (
          <p>Loading...</p>
        )}
      </Content>
    </Layout>
  );
}

export default EditReport;