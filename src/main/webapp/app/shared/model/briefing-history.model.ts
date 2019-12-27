import { Moment } from 'moment';
import { IBriefing } from 'app/shared/model/briefing.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { TypeOfBriefing } from 'app/shared/model/enumerations/type-of-briefing.model';

export interface IBriefingHistory {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  typeOfBriefing?: TypeOfBriefing;
  job?: IBriefing;
  department?: IDepartment;
  employee?: IEmployee;
}

export class BriefingHistory implements IBriefingHistory {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public typeOfBriefing?: TypeOfBriefing,
    public job?: IBriefing,
    public department?: IDepartment,
    public employee?: IEmployee
  ) {}
}
