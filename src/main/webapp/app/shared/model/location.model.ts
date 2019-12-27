import { IOrganization } from 'app/shared/model/organization.model';
import { RegionName } from 'app/shared/model/enumerations/region-name.model';

export interface ILocation {
  id?: number;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  regionName?: RegionName;
  organization?: IOrganization;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public regionName?: RegionName,
    public organization?: IOrganization
  ) {}
}
