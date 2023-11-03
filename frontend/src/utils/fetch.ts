import { RcFile } from "antd/es/upload";
import { BarebonesPrijava, Prijava, Response, TipOstecenja } from "./types";

export type PrijaveOptions = {
  active?: string;
  ostecenje_id?: string;
}

export function getPrijave(options?: PrijaveOptions): Promise<Response<Prijava[]>> {
  return new Promise(res => {
    fetch("/api/prijave?" + new URLSearchParams(options))
      .then(resp => resp.json())
      .then(r => res({
        ...r,
        prvoVrijemePrijave: new Date(r.prvoVrijemePrijave),
        vrijemeOtklona: r.vrijemeOtklona ? new Date(r.vrijemeOtklona) : undefined,
      }))
      .catch(error => res({ success: false, error }));
  });
}

export function getOstecenja(): Promise<Response<TipOstecenja[]>> {
  return new Promise(res => {
    fetch("/api/ostecenja")
      .then(resp => resp.json())
      .then(r => res(r))
      .catch(error => res({ success: false, error }));
  });
}

export function addPrijava(prijava: BarebonesPrijava, images: RcFile[]): Promise<Response<never>> {
  return new Promise(res => {
    const data = new FormData();
    
    data.append("data", JSON.stringify(prijava));
    images.forEach(im => data.append("files", im, im.name));

    fetch("/api/addPrijava", { method: "POST", body: data, })
      .then(resp => resp.json())
      .then(r => console.log(r))
      .catch(error => res({ success: false, error }));
  });
}