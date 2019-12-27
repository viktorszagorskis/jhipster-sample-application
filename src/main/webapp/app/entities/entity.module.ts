import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'organization',
        loadChildren: () => import('./organization/organization.module').then(m => m.JhipsterSampleApplicationOrganizationModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.JhipsterSampleApplicationLocationModule)
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.JhipsterSampleApplicationEmployeeModule)
      },
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.JhipsterSampleApplicationDepartmentModule)
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.JhipsterSampleApplicationTaskModule)
      },
      {
        path: 'briefing',
        loadChildren: () => import('./briefing/briefing.module').then(m => m.JhipsterSampleApplicationBriefingModule)
      },
      {
        path: 'briefing-history',
        loadChildren: () => import('./briefing-history/briefing-history.module').then(m => m.JhipsterSampleApplicationBriefingHistoryModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterSampleApplicationEntityModule {}
