import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BriefingHistoryUpdateComponent } from 'app/entities/briefing-history/briefing-history-update.component';
import { BriefingHistoryService } from 'app/entities/briefing-history/briefing-history.service';
import { BriefingHistory } from 'app/shared/model/briefing-history.model';

describe('Component Tests', () => {
  describe('BriefingHistory Management Update Component', () => {
    let comp: BriefingHistoryUpdateComponent;
    let fixture: ComponentFixture<BriefingHistoryUpdateComponent>;
    let service: BriefingHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BriefingHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BriefingHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BriefingHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BriefingHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BriefingHistory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new BriefingHistory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
