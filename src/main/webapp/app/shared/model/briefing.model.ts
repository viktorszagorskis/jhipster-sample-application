import { ITask } from 'app/shared/model/task.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IBriefing {
  id?: number;
  briefTitle?: string;
  scoring?: number;
  tasks?: ITask[];
  employee?: IEmployee;
}

export class Briefing implements IBriefing {
  constructor(
    public id?: number,
    public briefTitle?: string,
    public scoring?: number,
    public tasks?: ITask[],
    public employee?: IEmployee
  ) {}
}
