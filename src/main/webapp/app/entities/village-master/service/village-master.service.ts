import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVillageMaster, NewVillageMaster } from '../village-master.model';

export type PartialUpdateVillageMaster = Partial<IVillageMaster> & Pick<IVillageMaster, 'id'>;

export type EntityResponseType = HttpResponse<IVillageMaster>;
export type EntityArrayResponseType = HttpResponse<IVillageMaster[]>;

@Injectable({ providedIn: 'root' })
export class VillageMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/village-masters');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(villageMaster: NewVillageMaster): Observable<EntityResponseType> {
    return this.http.post<IVillageMaster>(this.resourceUrl, villageMaster, { observe: 'response' });
  }

  update(villageMaster: IVillageMaster): Observable<EntityResponseType> {
    return this.http.put<IVillageMaster>(`${this.resourceUrl}/${this.getVillageMasterIdentifier(villageMaster)}`, villageMaster, {
      observe: 'response',
    });
  }

  partialUpdate(villageMaster: PartialUpdateVillageMaster): Observable<EntityResponseType> {
    return this.http.patch<IVillageMaster>(`${this.resourceUrl}/${this.getVillageMasterIdentifier(villageMaster)}`, villageMaster, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVillageMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVillageMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getVillageMasterIdentifier(villageMaster: Pick<IVillageMaster, 'id'>): number {
    return villageMaster.id;
  }

  compareVillageMaster(o1: Pick<IVillageMaster, 'id'> | null, o2: Pick<IVillageMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getVillageMasterIdentifier(o1) === this.getVillageMasterIdentifier(o2) : o1 === o2;
  }

  addVillageMasterToCollectionIfMissing<Type extends Pick<IVillageMaster, 'id'>>(
    villageMasterCollection: Type[],
    ...villageMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const villageMasters: Type[] = villageMastersToCheck.filter(isPresent);
    if (villageMasters.length > 0) {
      const villageMasterCollectionIdentifiers = villageMasterCollection.map(
        villageMasterItem => this.getVillageMasterIdentifier(villageMasterItem)!
      );
      const villageMastersToAdd = villageMasters.filter(villageMasterItem => {
        const villageMasterIdentifier = this.getVillageMasterIdentifier(villageMasterItem);
        if (villageMasterCollectionIdentifiers.includes(villageMasterIdentifier)) {
          return false;
        }
        villageMasterCollectionIdentifiers.push(villageMasterIdentifier);
        return true;
      });
      return [...villageMastersToAdd, ...villageMasterCollection];
    }
    return villageMasterCollection;
  }
}
