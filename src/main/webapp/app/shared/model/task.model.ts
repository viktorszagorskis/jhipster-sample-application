import { IBriefing } from 'app/shared/model/briefing.model';

export interface ITask {
  id?: number;
  title?: string;
  description?: string;
  maxScoring?: number;
  h5pLink?: string;
  link?: string;
  briefings?: IBriefing[];
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public maxScoring?: number,
    public h5pLink?: string,
    public link?: string,
    public briefings?: IBriefing[]
  ) {}
}
