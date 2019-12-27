import { Moment } from 'moment';
import { IBriefing } from 'app/shared/model/briefing.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IDepartment } from 'app/shared/model/department.model';

export interface IEmployee {
  id?: number;
  umane?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  hireDate?: Moment;
  briefings?: IBriefing[];
  managers?: IEmployee[];
  employee?: IEmployee;
  departments?: IDepartment[];
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public umane?: string,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public hireDate?: Moment,
    public briefings?: IBriefing[],
    public managers?: IEmployee[],
    public employee?: IEmployee,
    public departments?: IDepartment[]
  ) {}
}
