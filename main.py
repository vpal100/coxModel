from lifelines.datasets import load_rossi
from lifelines import CoxPHFitter
import json

def create_lifeline_model():
    rossi = load_rossi()
    cph = CoxPHFitter()
    cph.fit(rossi, 'week', 'arrest')
    cph.print_summary()
    print(cph.params_)
    print(cph.baseline_hazard_)
    print(cph.summary)
    ret = dict()
    ret.update(cph.baseline_hazard_.to_dict())
    ret['parameters'] = cph.params_.to_dict()
    ret['mean'] = cph._norm_mean.to_dict()
    json_object = json.dumps(ret)
    with open(r"java\resources\cph_model_exports.json", 'w') as json_file:
        json_file.write(json_object)

if __name__ == '__main__':
    create_lifeline_model()

