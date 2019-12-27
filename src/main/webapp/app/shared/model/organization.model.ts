import { ILocation } from 'app/shared/model/location.model';

export interface IOrganization {
  id?: number;
  orgName?: string;
  orgRegNum?: string;
  locations?: ILocation[];
}

export class Organization implements IOrganization {
  constructor(public id?: number, public orgName?: string, public orgRegNum?: string, public locations?: ILocation[]) {}
}
