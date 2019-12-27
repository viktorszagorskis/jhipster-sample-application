import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { BriefingDetailComponent } from 'app/entities/briefing/briefing-detail.component';
import { Briefing } from 'app/shared/model/briefing.model';

describe('Component Tests', () => {
  describe('Briefing Management Detail Component', () => {
    let comp: BriefingDetailComponent;
    let fixture: ComponentFixture<BriefingDetailComponent>;
    const route = ({ data: of({ briefing: new Briefing(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [BriefingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BriefingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BriefingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load briefing on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.briefing).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
