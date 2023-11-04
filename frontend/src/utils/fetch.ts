import { RcFile } from "antd/es/upload";
import { BarebonesPrijava, Prijava, Response, TipOstecenja } from "./types";

export type PrijaveOptions = {
  active?: string;
  ostecenjeId?: string;
}

export function getPrijave(options?: PrijaveOptions): Promise<Response<Prijava[]>> {
  return new Promise(res => {
    fetch("/api/prijave?" + new URLSearchParams(options))
      .then(resp => resp.json())
      .then(r => res(r))
      .catch(error => res({ success: false, error }));
  });
}

export function getUserPrijave(id: string): Promise<Response<Prijava[]>> {
  return new Promise(res => {
    fetch("/api/prijave?" + new URLSearchParams({ id }))
      .then(resp => resp.json())
      .then(r => res(r))
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
    
    Object.entries(prijava).forEach(entry => data.append(entry[0], JSON.stringify(entry[1])));
    images.forEach(im => data.append("files", im, im.name));

    fetch("/api/addPrijava", { method: "POST", body: data, })
      .then(resp => resp.json())
      .then(r => console.log(r))
      .catch(error => res({ success: false, error }));
  });
}