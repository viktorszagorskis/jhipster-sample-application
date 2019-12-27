import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BriefingUpdateComponent } from 'app/entities/briefing/briefing-update.component';
import { BriefingService } from 'app/entities/briefing/briefing.service';
import { Briefing } from 'app/shared/model/briefing.model';

describe('Component Tests', () => {
  describe('Briefing Management Update Component', () => {
    let comp: BriefingUpdateComponent;
    let fixture: ComponentFixture<BriefingUpdateComponent>;
    let service: BriefingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BriefingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BriefingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BriefingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BriefingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Briefing(123);
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
        const entity = new Briefing();
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
