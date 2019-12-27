import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBriefingHistory, BriefingHistory } from 'app/shared/model/briefing-history.model';
import { BriefingHistoryService } from './briefing-history.service';
import { IBriefing } from 'app/shared/model/briefing.model';
import { BriefingService } from 'app/entities/briefing/briefing.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

type SelectableEntity = IBriefing | IDepartment | IEmployee;

@Component({
  selector: 'jhi-briefing-history-update',
  templateUrl: './briefing-history-update.component.html'
})
export class BriefingHistoryUpdateComponent implements OnInit {
  isSaving = false;

  jobs: IBriefing[] = [];

  departments: IDepartment[] = [];

  employees: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    startDate: [],
    endDate: [],
    typeOfBriefing: [],
    job: [],
    department: [],
    employee: []
  });

  constructor(
    protected briefingHistoryService: BriefingHistoryService,
    protected briefingService: BriefingService,
    protected departmentService: DepartmentService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ briefingHistory }) => {
      this.updateForm(briefingHistory);

      this.briefingService
        .query({ filter: 'briefinghistory-is-null' })
        .pipe(
          map((res: HttpResponse<IBriefing[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IBriefing[]) => {
          if (!briefingHistory.job || !briefingHistory.job.id) {
            this.jobs = resBody;
          } else {
            this.briefingService
              .find(briefingHistory.job.id)
              .pipe(
                map((subRes: HttpResponse<IBriefing>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBriefing[]) => {
                this.jobs = concatRes;
              });
          }
        });

      this.departmentService
        .query({ filter: 'briefinghistory-is-null' })
        .pipe(
          map((res: HttpResponse<IDepartment[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDepartment[]) => {
          if (!briefingHistory.department || !briefingHistory.department.id) {
            this.departments = resBody;
          } else {
            this.departmentService
              .find(briefingHistory.department.id)
              .pipe(
                map((subRes: HttpResponse<IDepartment>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDepartment[]) => {
                this.departments = concatRes;
              });
          }
        });

      this.employeeService
        .query({ filter: 'briefinghistory-is-null' })
        .pipe(
          map((res: HttpResponse<IEmployee[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEmployee[]) => {
          if (!briefingHistory.employee || !briefingHistory.employee.id) {
            this.employees = resBody;
          } else {
            this.employeeService
              .find(briefingHistory.employee.id)
              .pipe(
                map((subRes: HttpResponse<IEmployee>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEmployee[]) => {
                this.employees = concatRes;
              });
          }
        });
    });
  }

  updateForm(briefingHistory: IBriefingHistory): void {
    this.editForm.patchValue({
      id: briefingHistory.id,
      startDate: briefingHistory.startDate != null ? briefingHistory.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: briefingHistory.endDate != null ? briefingHistory.endDate.format(DATE_TIME_FORMAT) : null,
      typeOfBriefing: briefingHistory.typeOfBriefing,
      job: briefingHistory.job,
      department: briefingHistory.department,
      employee: briefingHistory.employee
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const briefingHistory = this.createFromForm();
    if (briefingHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.briefingHistoryService.update(briefingHistory));
    } else {
      this.subscribeToSaveResponse(this.briefingHistoryService.create(briefingHistory));
    }
  }

  private createFromForm(): IBriefingHistory {
    return {
      ...new BriefingHistory(),
      id: this.editForm.get(['id'])!.value,
      startDate:
        this.editForm.get(['startDate'])!.value != null ? moment(this.editForm.get(['startDate'])!.value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate'])!.value != null ? moment(this.editForm.get(['endDate'])!.value, DATE_TIME_FORMAT) : undefined,
      typeOfBriefing: this.editForm.get(['typeOfBriefing'])!.value,
      job: this.editForm.get(['job'])!.value,
      department: this.editForm.get(['department'])!.value,
      employee: this.editForm.get(['employee'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBriefingHistory>>): void {
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
}
