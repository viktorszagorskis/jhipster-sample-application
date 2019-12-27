import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBriefing, Briefing } from 'app/shared/model/briefing.model';
import { BriefingService } from './briefing.service';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

type SelectableEntity = ITask | IEmployee;

@Component({
  selector: 'jhi-briefing-update',
  templateUrl: './briefing-update.component.html'
})
export class BriefingUpdateComponent implements OnInit {
  isSaving = false;

  tasks: ITask[] = [];

  employees: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    briefTitle: [],
    scoring: [],
    tasks: [],
    employee: []
  });

  constructor(
    protected briefingService: BriefingService,
    protected taskService: TaskService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ briefing }) => {
      this.updateForm(briefing);

      this.taskService
        .query()
        .pipe(
          map((res: HttpResponse<ITask[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITask[]) => (this.tasks = resBody));

      this.employeeService
        .query()
        .pipe(
          map((res: HttpResponse<IEmployee[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEmployee[]) => (this.employees = resBody));
    });
  }

  updateForm(briefing: IBriefing): void {
    this.editForm.patchValue({
      id: briefing.id,
      briefTitle: briefing.briefTitle,
      scoring: briefing.scoring,
      tasks: briefing.tasks,
      employee: briefing.employee
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const briefing = this.createFromForm();
    if (briefing.id !== undefined) {
      this.subscribeToSaveResponse(this.briefingService.update(briefing));
    } else {
      this.subscribeToSaveResponse(this.briefingService.create(briefing));
    }
  }

  private createFromForm(): IBriefing {
    return {
      ...new Briefing(),
      id: this.editForm.get(['id'])!.value,
      briefTitle: this.editForm.get(['briefTitle'])!.value,
      scoring: this.editForm.get(['scoring'])!.value,
      tasks: this.editForm.get(['tasks'])!.value,
      employee: this.editForm.get(['employee'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBriefing>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: ITask[], option: ITask): ITask {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
