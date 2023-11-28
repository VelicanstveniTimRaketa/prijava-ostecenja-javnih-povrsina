import { notification } from "antd";
import { Location } from "./types";

export function googleToLocation(latlng: google.maps.LatLng): Location {
  return { latitude: latlng.lat(), longitude: latlng.lng() };
}

export function locationToGoogle(latlng: Location): google.maps.LatLngLiteral {
  return { lat: latlng.latitude, lng: latlng.longitude };
}

export function getLocation(): Promise<google.maps.LatLngLiteral | undefined> {
  return new Promise((res) => {
    if (!navigator.geolocation) {
      notification.error({
        message: "Vaša lokacija nije dohvaćena",
        description: "Vaš preglednik ne podržava dohvaćanje lokacije. Molimo ažurirajte preglednik.",
        placement: "top",
      });
      res(undefined);
    }

    navigator.geolocation.getCurrentPosition(pos => {
      res(locationToGoogle(pos.coords));
    }, error => {
      res(undefined);
      if (error.code == error.PERMISSION_DENIED) {
        notification.warning({
          message: "Vaša lokacija nije dohvaćena",
          description: "Onemogućili ste dohvaćanje Vaše lokacije. Molimo unesite " +
            "lokaciju na neki drugi način ili omogućite dohvaćanje lokacije.",
          placement: "top",
        });
      } else {
        notification.error({
          message: "Vaša lokacija nije dohvaćena",
          description: "Dogodila se pogreška pri dohvaćanju lokacije. Pokušajte ponovno kasnije.",
          placement: "top",
        });
      }
    });
  });
}
