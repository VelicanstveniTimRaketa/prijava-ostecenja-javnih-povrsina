import { Layout, theme, Modal, Typography, notification } from "antd";
import { Content } from "antd/es/layout/layout";
import { useEffect, useState } from "react";
import { RcFile } from "antd/es/upload";
import NewReportForm from "../components/NewReportForm";
import { AddPrijavaResponse, BarebonesPrijava, Prijava, Response } from "../utils/types";
import { addPrijava, connectPrijave } from "../utils/fetch";
import { useNavigate } from "react-router";
import Title from "antd/es/typography/Title";
import ReportList from "../components/ReportList";

interface NewReportProps {
  initialData?: Prijava;
}

function NewReport(props: NewReportProps) {
  const { token: { colorBgContainer } } = theme.useToken();
  const [response, setResponse] = useState<Response<AddPrijavaResponse>>();
  const navigate = useNavigate();
  const [images, setImages] = useState<{ originFileObj: RcFile }[]>([]);
  const [loading, setLoading] = useState(false);

  function success(message: string) {
    notification.success({
      message,
      description: "",
      placement: "top",
    });
    navigate("/search");
  }

  function fail(response: Response<unknown>) {
    notification.error({
      message: "Prijava nije registrirana",
      description: response.errors && response.errors[0],
      placement: "top",
    });
  }

  

  useEffect(() => {
    if (!response) return;

    if (!response.success) {
      fail(response);
      return;
    }
    if (response.data && response.data.nearbyReports.length > 0) return;

    success("Prijava uspješno registrirana");
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [navigate, response]);

  async function onSubmit(prijava: BarebonesPrijava) {
    setLoading(true);
    addPrijava(prijava, images.map(image => image.originFileObj)).then(v => {
      setResponse(v);
      setLoading(false);
    });
  }


  return (
    <Layout style={{ margin: "3em", display: "flex", alignItems: "center" }}>
      <Content
        style={{
          display: "flex",
          flexDirection: "column",
          maxWidth: "100%",
          alignItems: "center",
          padding: "2em 6em",
          background: colorBgContainer,
        }}
      >
        <Title level={2}>Nova prijava</Title>
        <NewReportForm onSubmit={onSubmit} setImages={setImages} loading={loading}/>
      </Content>
      {response?.data && response.data.nearbyReports.length !== 0 && (
        <Modal
          style={{ minWidth: "fit-content" }}
          open={!!response}
          cancelText="Preskoči"
          onCancel={() => navigate("/search")}
          footer={(_, { CancelBtn }) => <CancelBtn />}
        >
          <Typography.Title level={2}>Pronađene su prijave u blizini!</Typography.Title>
          <Typography.Title level={5}>Pogledajte odnosi li se ikoja na Vašu i povežite svoju prijavu s njome.</Typography.Title>
          <ReportList buttonText="Poveži" data={response.data.nearbyReports} onButtonClick={(p) => {
            connectPrijave(response.data?.newReport.id as number, p.id).then(res => {
              res.success ? success("Prijava uspješno registrirana i povezana") : fail(res);
            });
          }} />
        </Modal>
      )}
    </Layout>
  );
}

export default NewReport;
